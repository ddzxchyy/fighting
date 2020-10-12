# IOC

IOC（Inversion of control）控制反转。控制反转是一种思想而不是一种技术实现。它指的是将创建对象的权力交给外部环境。

**控制**：创建对象的权力。

**反转**：将控制权交给外部环境，比如 IOC 容器（一个对象的工厂，是 Spring 用来实现 IOC 的载体， IOC 容器实际上就是个 Map（key，value）,Map 中存放的是各种对象。）。



## IOC 的作用

解决对象的创建以及管理的问题。

在没有使用 IOC 思想的情况下， 类 A 依赖类 B， 如果直接 new B（），将产生高度耦合的结果。如果类 A 使用了 类 B 的方法 methodB，而 methodB  又依赖类 C, 在这种情况下导致类 A 也需要依赖类 C 才能实现 methodB 的功能。



## 依赖注入

IOC 最常见的实现方式是通过依赖注入（Dependency Injection 简称 DI）。



## 构造器注入的优势

1. 依赖不可变。
2. 依赖不可为空。setter 注入如果不在 IOC 容器环境，就有空指针的风险。
3. 避免循环依赖，在 spring 项目启动的时候就会报错。
