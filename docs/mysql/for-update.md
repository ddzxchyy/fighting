# for update 

for update 在查询的时候为行加上排它锁，获取给表上表级锁。

for update  仅适用于InnoDB，并且必须开启事务，在begin与commit之间才生效。

 InnoDB 默认是行级锁，当有明确指定的主键/索引时候，是行级锁，否则是表级锁。



现有如下 user 表

| 字段    | 类型    | 主键 | 索引 |
| ------- | ------- | ---- | ---- |
| id      | int     | 1    | 1    |
| name    | varchar | 0    | 0    |
| user_id | int     | 0    | 1    |
| num     | int     | 0    | 0    |

我们来进行验证。

## 行级锁验证

验证指定主键查询的情况下加行级锁

| 进程 1                                      | 进程 2                                                      |
| :------------------------------------------ | :---------------------------------------------------------- |
| begin;                                      |                                                             |
| select * from user where id = 1 for update; |                                                             |
|                                             | update user set num = num - 1 where id = 1; # 等待          |
|                                             | update user set num = num - 1 where id = 2; # 成功          |
|                                             | select * from user where id = 1; # 成功，但不能进行更新操作 |
|                                             | select * from user where id = 1 for update; # 等待          |
| commit;                                     |                                                             |
|                                             | 执行等待的任务，成功                                        |

验证指定索引查询的情况下加行级锁

| 进程 1                                           | 进程 2                                                  |
| :----------------------------------------------- | :------------------------------------------------------ |
| begin;                                           |                                                         |
| select * from user where user_id = 1 for update; |                                                         |
|                                                  | select * from user where user_id = 1 for update; # 等待 |
|                                                  | select * from user where user_id = 2 for update; # 成功 |
|                                                  |                                                         |
|                                                  |                                                         |
| commit;                                          |                                                         |
|                                                  | 执行等待的任务，成功                                    |





## 表级锁验证

验证非主键或索引查询的情况下加行级锁

| 进程 1                                                       | 进程 2                                                       |
| :----------------------------------------------------------- | :----------------------------------------------------------- |
| begin;                                                       |                                                              |
| select * from user where name =' jzq1' for update; # 给整张表上了排它锁； |                                                              |
|                                                              | update user_test set name = 'jzq22' where name = 'jzq2'; # 等待 |
|                                                              | update user_test set name = 'jzq11' where name = 'jzq11'; # 等待 |
| commit;                                                      |                                                              |
|                                                              | 执行等待的任务，成功                                         |

