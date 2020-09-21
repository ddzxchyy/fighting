# Spring-Security

原创出处 http://www.iocoder.cn/Spring-Boot/Spring-Security/ 「芋道源码」

## 概述

基本上，在所有的开发的系统中，都必须做认证(authentication)和授权(authorization)，以保证系统的安全性。

官网的介绍如下

> FROM [《Spring Security 官网》](https://spring.io/projects/spring-security)
>
> Spring Security is a powerful and highly customizable authentication and access-control framework. It is the de-facto standard for securing Spring-based applications.
> Spring Security 是一个功能强大且高度可定制的身份验证和访问控制框架。它是用于保护基于 Spring 的应用程序。
>
> Spring Security is a framework that focuses on providing both authentication and authorization to Java applications. Like all Spring projects, the real power of Spring Security is found in how easily it can be extended to meet custom requirements
> Spring Security 是一个框架，侧重于为 Java 应用程序提供身份验证和授权。与所有 Spring 项目一样，Spring 安全性的真正强大之处，在于它很容易扩展以满足定制需求。



### 认证和授权的区别

认证解决“你是谁”的问题，授权解决“你能做什么”的问题

> - authentication [ɔ,θɛntɪ’keʃən] 认证
> - authorization [,ɔθərɪ’zeʃən] 授权
>
> 以**打飞机**举例子：
>
> - 【认证】你要登机，你需要出示你的 passport 和 ticket，passport 是为了证明你张三确实是你张三，这就是 authentication。
> - 【授权】而机票是为了证明你张三确实买了票可以上飞机，这就是 authorization。
>
> 以**论坛**举例子：
>
> - 【认证】你要登录论坛，输入用户名张三，密码 1234，密码正确，证明你张三确实是张三，这就是 authentication。
> - 【授权】再一 check 用户张三是个版主，所以有权限加精删别人帖，这就是 authorization 。



## 快速入门

添加依赖

```xml
   <!-- 实现对 Spring Security 的自动化配置 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
```

添加配置

```yml
spring:
  # Spring Security 配置项，对应 SecurityProperties 配置类
  security:
    # 配置默认的 InMemoryUserDetailsManager 的用户账号与密码。
    user:
      name: user # 账号
      password: user # 密码
      roles: ADMIN # 拥有角色
```

默认情况下，Spring Boot [UserDetailsServiceAutoConfiguration](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/security/servlet/UserDetailsServiceAutoConfiguration.java) 自动化配置类，会创建一个**内存级别**的 [InMemoryUserDetailsManager](https://github.com/spring-projects/spring-security/blob/master/core/src/main/java/org/springframework/security/provisioning/InMemoryUserDetailsManager.java) Bean 对象，提供认证的用户信息。

```java
@RequestMapping("/admin")
@RestController
public class AdminController {

    @GetMapping("/hello")
    public R hello() {
        return R.ok("hello security");
    }

}

```

访问 hello 接口，因为没有登录，所以会被 Spring Security 拦截到登录界面。

<img alt="默认登录界面" src="http://www.iocoder.cn/images/Spring-Boot/2020-01-01/01.png"></img>

因为我们没有**自定义**登录界面，所以默认会使用 [DefaultLoginPageGeneratingFilter](https://github.com/spring-projects/spring-security/blob/master/web/src/main/java/org/springframework/security/web/authentication/ui/DefaultLoginPageGeneratingFilter.java) 类，生成上述界面。

录完成后，因为 Spring Security 会记录被拦截的访问地址，所以浏览器自动动跳转到 hello 接口。