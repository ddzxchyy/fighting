# Spring Boot 日志集成 Logging

[原文地址：芋道源码](http://www.iocoder.cn/Spring-Boot/Logging/)

## 1. 简介

### 1.1 日志框架

在 Java 日志框架的生态中，一共存在二种角色：**日志门面框架**和**日志实现框架**。

日志门面框架**仅**提供通用的日志 API 给调用方, 日志实现框架的配置需要自己添加。

目前，比较主流的选择是，选择 SLF4J 作为日志门面框架，Logback 作为日志实现框。

[门面模式](https://time.geekbang.org/column/article/206409)

<img src="http://www.iocoder.cn/images/Spring-Boot/2020-03-01/01.png">



### 1.2 SL4J 原理

解释原理的官方文档[《SLF4J 文档 —— Bridging legacy APIs》](http://www.slf4j.org/legacy.html)

一、 SL4J + Logback 组合

<img src="http://www.iocoder.cn/images/Spring-Boot/2020-03-01/03.png">



【红框】将使用**其它**日志**门面**框架的，替换成 **SLF4J** 日志门面框架。例如说，Spring 采用 JCL 日志门面框架，就是通过该方式来解决的。

!> [`jcl-over-slf4j`](https://mvnrepository.com/artifact/org.slf4j/jcl-over-slf4j) 库，将调用 JCL 打日志的地方，适配成调用 SLF4J API。实现原理是，`jcl-over-slf4j` 通过在包名／类名／方法名上和 JCL **保持完全一致**，内部重写来调用的是 SLF4J 的 API 。

【绿框】将使用**其它**日志**实现**框架的，替换成 **SLF4J** 日志门面框架。

[`log4j-over-slf4j`](https://mvnrepository.com/artifact/org.slf4j/log4j-over-slf4j) 库，将调用 Log4j1 打日志的地方，适配成调用 SLF4J API。实现原理同 `jcl-over-slf4j` 库。

[`jul-over-slf4j`](https://mvnrepository.com/artifact/org.slf4j/jul-to-slf4j) 库，将调用 JUL 打日志的地方，适配成调用 SLF4J API。实现原理是，实现自定义的 `java.util.logging.Handler` 处理器，来调用的是 SLF4J 的 API 。如下图所示：

【黄框】**SLF4J** 日志门面框架，调用具体的日志实现框架，打印日志。

 

### 1.3 Spring Boot 对日志框架的封装

在 Spring Boot 内部，保持和 Spring 一致，采用 JCL 日志**门面**框架来记录日志。但是，其 [`logging`](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot/src/main/java/org/springframework/boot/logging/package-info.java) 包下，提供了对 JUL、Log4j2、Logback 日志**实现**框架的封装，提供默认的日志配置。



## 2. 快速入门

### 2.1 环境准备

在 [`pom.xml`](https://github.com/YunaiV/SpringBoot-Labs/tree/master/lab-37/lab-37-logging-demo/pom.xml) 文件中，引入相关依赖。`spring-boot-starter-web` 包含了 `spring-boot-starter` ，而 `spring-boot-starter` 又已经包含了 `spring-boot-starter-logging`。

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```



### 2.2 配置

在 [`application.yml`](https://github.com/YunaiV/SpringBoot-Labs/blob/master/lab-37/lab-37-logging-demo/src/main/resources/application.yaml) 中，添加日志相关配置，如下：

```yml
spring:
  application:
    name: common-logging # 应用名

logging:
  # 日志文件配置
  file:
#    path: /Users/mrx/logs/ # 日志文件路径。
    name: /Users/mrx/logs/${spring.application.name}.log # 日志文件名。
    max-history: 7 # 日志文件要保留的归档的最大天数。默认为 7 天。
    max-size: 10MB # 日志文件的最大大小。默认为 10MB 。

  # 日志级别
  level:
    cn:
      com:
        springboot:
          lab37:
            loggingdemo:
              controller: DEBUG
```

**① 日志文件**

在 `logging.file.*` 配置项下，设置 Spring Boot **日志文件**。

默认情况下，Spring Boot 日志只会打印到控制台。所以需要通过 `logging.file.path` 或 `logging.file.name` 配置项来设置。不过要注意，这两者是**二选一**，并不是共同作用。

- `logging.file.name` ：日志文件全路径名。可以是相对路径，也可以是绝对路径。这里，我们设置为 `"/Users/yunai/logs/${spring.application.name}.log"` ，绝对路径。
- `logging.file.path` ：日志文件目录。会在该目录下，创建 `spring.log` 日志文件。例如说：`/Users/yunai/logs/` 。
- 上述两者都配置的情况下，以 `logging.file.name` 配置项为准。

## FAQ

SL4J 如何解决调用者依赖不同的日志框架

答：通过在 包名 / 类名 / 方法名 和 其他框架完全一致的方式（import 导入的时候路径完全一致，就不会有问题了）内部重写实现，调用 SL4J 的 API 。 jul 是实现自定义处理器。