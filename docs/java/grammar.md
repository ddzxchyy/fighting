# JAVA 语法



## hashCode 与 equals

hashCode() 的作用是获取哈希码，也称为散列码；它实际上返回一个 int 整型。这个哈希码的作用是确定该对象在哈希表的索引位置。



**为什么要有 hashCode ？**

以 HashSet 举例，当你把对象加入 hashSet 时，hashSet 会判断 hashcode 值是否相同，如果不同则直接加入，如果相同再调用 equals 方法判断，不相登的话，再重新散列到其它位置。这样就大大减少了 equals 方法的调用次数。