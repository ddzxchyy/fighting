#  更新语句如何执行

完整的流程图

<img src="https://upload-images.jianshu.io/upload_images/5148507-ce39f679f490b64f.png" />



<img src="https://static001.geekbang.org/resource/image/2e/be/2e5bff4910ec189fe1ee6e2ecc7b4bbe.png" />

 mysql 不是每次数据更改都立刻写到磁盘，而是会先将修改后的结果暂存在内存中,当一段时间后，再一次性将多个修改写到磁盘上，减少磁盘 io 成本，同时提高操作速度。 



##  如何保证事务	

mysql通过WAL(write-ahead logging)技术保证事务。在同一个事务中，每当数据库进行修改数据操作时，将修改结果更新到内存后，会在redo log添加一行记录记录“需要在哪个数据页上做什么修改”，并将该记录状态置为prepare，等到 commit 提交事务后，会将此次事务中在 redo log 添加的记录的状态都置为commit状态，之后将修改落盘时，会将 redo log 中状态为 commit 的记录的修改都写入磁盘



## FAQ

1、为什么 redo log 需要二阶段提交？

为了保证和 binlog 逻辑一致。

如果先写 binlog，再写 redo log， 那么在 binlog 写完之后 crash，由于 redo log 还没写，崩溃恢复以后这个事务无效，所以这一行 c 的值是 0。但是 binlog 里面已经记录了“把 c 从 0 改成 1”这个日志。所以，在之后用 binlog 来恢复的时候就多了一个事务出来，恢复出来的这一行 c 的值就是 1，与原库的值不同。   

如果先写 redo log， 那么在 redo log 写完后，mysql 进程异常，导致 binlog 还没有写。如果需要用这个 binlog 来恢复临时库的话， 就丢失了这一次更新。

2、 有了 redo log 为什么还需要 binlog

（1）redo log的大小是固定的，日志上的记录修改落盘后，日志会被覆盖掉，无法用于数据回滚/数据恢复等操作。
（2）redo log 是 innodb 引擎层实现的，并不是所有引擎都有。 

