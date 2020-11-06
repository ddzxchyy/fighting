# 一条 SQL 查询语句是如何执行的

[原文1](https://time.geekbang.org/column/article/68319)

[原文2](https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247485097&idx=1&sn=84c89da477b1338bdf3e9fcd65514ac1&chksm=cea24962f9d5c074d8d3ff1ab04ee8f0d6486e3d015cfd783503685986485c11738ccb542ba7&token=79317275&lang=zh_CN#rd)

看一个事儿千万不要直接陷入细节里，你应该先鸟瞰其全貌，这样能够帮助你从高维度理解问题。同样，对于 MySQL 的学习也是这样。

SQL 执行过程分为两类：

* 一类对于查询等过程如下：权限校验 -- > 查询缓存 -- > 分析器 -- > 优化器 -- > 权限校验 -- > 执行器--- > 引擎
* 对于更新等语句执行流程如下：分析器 -- > 权限校验 -- > 执行器 -- > 引擎 -- > redo log prepare -- > binlog  -- > redo log commit

## mysql 架构

mysql 架构可以分为两部分：Sever 层和存储引擎层。

Server 层包括连接器、查询缓存（mysql 8.0 + 已经移除）、 分析器、优化器、执行器。

存储引擎负责数据的存储和读取。其架构模式是插件式的，支持 InnoDB、MyISAM、Memory 等多个存储引擎。

<img src="https://static001.geekbang.org/resource/image/0d/d9/0d2070e8f84c4801adbfa03bda1f98d9.png" alt="mysql 架构图" />



### 1.1 连接器

**身份认证和权限相关(登录 MySQL 的时候)。**

第一步，你会先连接到这个数据库上，这时候接待你的就是连接器。连接器负责跟客户端建立连接、获取权限、维持和管理连接。连接命令一般是这么写的

```sql
mysql -h 主机地址 -u 用户名 -p 用户密码
```

输完命令之后，你就需要在交互对话里面输入密码。虽然密码也可以直接跟在 -p 后面写在命令行中，但这样可能会导致你的密码泄露。如果你连的是生产服务器，强烈建议你不要这么做。

连接命令中的 mysql 是客户端工具，用来跟服务端建立连接。在完成经典的 TCP 握手后，连接器就要开始认证你的身份，这个时候用的就是你输入的用户名和密码。

 如果用户名密码认证通过，连接器会到权限表里面查出你拥有的权限。之后，这个连接里面的权限判断逻辑，都将依赖于此时读到的权限。

!> 这就意味着，一个用户成功建立连接后，即使你用管理员账号对这个用户的权限做了修改，也不会影响已经存在连接的权限。修改完成后，只有再新建的连接才会使用新的权限设置。

完成连接后，如果没有后续动作，这个连接就处于空闲状态（Sleep）。

| Host          | db         | Command | Time | State | Info |
| ------------- | ---------- | ------- | ---- | ----- | ---- |
| 127.0.0.1:xxx | 数据库名称 | Sleep   | 100  |       |      |

客户端如果太长时间没动静，连接器就会自动将它断开。这个时间是由参数 `wait_timeout` 控制的，默认值是 8 小时。

如果在连接被断开之后，客户端再次发送请求的话，就会收到一个错误提醒： Lost connection to MySQL server during query。这时候如果你要继续，就需要重连，然后再执行请求了。

!> 数据库里面，长连接是指连接成功后，如果客户端持续有请求，则一直使用同一个连接。短连接则是指每次执行完很少的几次查询就断开连接，下次查询再重新建立一个。

但是全部使用长连接后，你可能会发现，有些时候 MySQL 占用内存涨得特别快，这是因为 **MySQL 在执行过程中临时使用的内存是管理在连接对象里面的。这些资源会在连接断开的时候才释放**。所以如果长连接累积下来，可能导致内存占用太大，被系统强行杀掉（OOM），从现象看就是 MySQL 异常重启了。



### 1.2 分析器

**析器说白了就是要先看你的 SQL 语句要干嘛，再检查你的 SQL 语句语法是否正确。**

**第一步，词法分析**，一条 SQL 语句有多个字符串组成，首先要提取关键字，比如 select，提出查询的表，提出字段名，提出查询条件等等。做完这些操作后，就会进入第二步。

**第二步，语法分析**，主要就是判断你输入的 sql 是否正确，是否符合 MySQL 的语法。

完成这 2 步之后，MySQL 就准备开始执行了，但是如何执行，怎么执行是最好的结果呢？这个时候就需要优化器上场了。

### 1.3 优化器

 **按照 MySQL 认为最优的方案去执行。**

经过了分析器，MySQL 就知道你要做什么了。在开始执行之前，还要先经过优化器的处理。优化器是在表里面有多个索引的时候，决定使用哪个索引；或者在一个语句有多表关联（join）的时候，决定各个表的连接顺序。比如你执行下面这样的语句，这个语句是执行两个表的 join：

```sql
select * from t1 join t2 using(ID)  where t1.c=10 and t2.d=20;
```

* 既可以先从表 t1 里面取出 c=10 的记录的 ID 值，再根据 ID 值关联到表 t2，再判断 t2 里面 d 的值是否等于 20。

* 也可以先从表 t2 里面取出 d=20 的记录的 ID 值，再根据 ID 值关联到 t1，再判断 t1 里面 c 的值是否等于 10。

这两种执行方法的逻辑结果是一样的，但是执行的效率会有不同，而优化器的作用就是决定选择使用哪一个方案。

###  执行器

**执行语句，然后从存储引擎返回数据。**

MySQL 通过分析器知道了你要做什么，通过优化器知道了该怎么做，于是就进入了执行器阶段，开始执行语句。

当选择了执行方案后，MySQL 就准备开始执行了，首先执行前会校验该用户有没有权限，如果没有权限，就会返回错误信息，如果有权限，就会去调用引擎的接口，返回接口执行的结果。

## FAQ

1、使用长连接 Mysql 占用的内存特别大如何处理？

（1）定期断开长连接。使用一段时间，或者程序里面判断执行过一个占用内存的大查询后，断开连接，之后要查询再重连。

（2）如果你用的是 MySQL 5.7 +，可以在每次执行一个比较大的操作后，通过执行 mysql_reset_connection 来重新初始化连接资源。这个过程不需要重连和重新做权限验证，但是会将连接恢复到刚刚创建完时的状态。