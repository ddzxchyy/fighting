# Spring Security

[原文地址：芋道源码]( http://www.iocoder.cn/Spring-Boot/Spring-Security/ )



## 1、简介

Spring Security 是一个框架，侧重于为 Java 应用程序提供身份认证(authentication)和授权(authorization)。官网介绍如下：

> FROM [《Spring Security 官网》](https://spring.io/projects/spring-security)
>
> Spring Security is a powerful and highly customizable authentication and access-control framework. It is the de-facto standard for securing Spring-based applications.
> Spring Security 是一个功能强大且高度可定制的身份验证和访问控制框架。它是用于保护基于 Spring 的应用程序。
>
> Spring Security is a framework that focuses on providing both authentication and authorization to Java applications. Like all Spring projects, the real power of Spring Security is found in how easily it can be extended to meet custom requirements
> Spring Security 是一个框架，侧重于为 Java 应用程序提供身份验证和授权。与所有 Spring 项目一样，Spring 安全性的真正强大之处，在于它很容易扩展以满足定制需求。



###  认证和授权的区别 

 **认证解决“你是谁”的问题，授权解决“你能做什么”的问题**

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



## 2、快速入门

### 环境准备 

添加依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```



### 设置

在 `application.yml` 文件中添加如下配置：

```yml
spring:
  # Spring Security 配置项，对应 SecurityProperties 配置类
  security:
    # 配置默认的 InMemoryUserDetailsManager 的用户账号与密码。
    user:
      name: admin # 账号
      password: admin # 密码
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

登录完成后，因为 Spring Security 会记录被拦截的访问地址，所以浏览器自动动跳转到 hello 接口。

## 3、进阶篇

在进阶篇，我们实现来 spring security 的认证和授权功能。

### 实现认证管理器

```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 实现 AuthenticationManager 认证管理器
     * @param auth a
     * @throws Exception e
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                // <X> 使用内存中的 InMemoryUserDetailsManager
                .inMemoryAuthentication()
                // <Y> 不使用 PasswordEncoder 密码编码器
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                // <Z> 配置 admin 用户
                .withUser("admin").password("admin").roles("ADMIN")
                // <Z> 配置 normal 用户
                .and().withUser("normal").password("normal").roles("NORMAL");
    }
}
```

`<X>` 处，调用 `AuthenticationManagerBuilder#inMemoryAuthentication()` 方法，使用**内存级别**的 [InMemoryUserDetailsManager](https://github.com/spring-projects/spring-security/blob/master/core/src/main/java/org/springframework/security/provisioning/InMemoryUserDetailsManager.java) Bean 对象，提供认证的用户信息。

实际项目中，我们更多采用调用 `AuthenticationManagerBuilder#userDetailsService(userDetailsService)` 方法，使用自定义实现的 [UserDetailsService](https://github.com/spring-projects/spring-security/blob/master/core/src/main/java/org/springframework/security/core/userdetails/UserDetailsService.java) 实现类，更加**灵活**且**自由**的实现认证的用户信息的读取。



### 实现 URL 权限控制

重写 `#configure(HttpSecurity http)` 方法，来配置 URL 的权限控制。

```java

// SecurityConfig
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
            // <X> 配置请求地址的权限
            .authorizeRequests()
                .antMatchers("/test/echo").permitAll() // 所有用户可访问
                .antMatchers("/test/admin").hasRole("ADMIN") // 需要 ADMIN 角色
                .antMatchers("/test/normal").access("hasRole('ROLE_NORMAL')") // 需要 NORMAL 角色。
                // 任何请求，访问的用户都需要经过认证
                .anyRequest().authenticated()
            .and()
            // <Y> 设置 Form 表单登录
            .formLogin()
//                    .loginPage("/login") // 登录 URL 地址
                .permitAll() // 所有用户可访问
            .and()
            // 配置退出相关
            .logout()
//                    .logoutUrl("/logout") // 退出 URL 地址
                .permitAll(); // 所有用户可访问
}
```

`<X>` 处，调用 `HttpSecurity#authorizeRequests()` 方法，开始配置 URL 的**权限控制**。注意看艿艿配置的**四个**权限控制的配置。下面，是配置权限控制会使用到的方法：

- `#(String... antPatterns)` 方法，配置匹配的 URL 地址，基于 [Ant 风格路径表达式](https://blog.csdn.net/songdexv/article/details/7219686) ，可传入多个。
- 【常用】`#permitAll()` 方法，所有用户可访问。
- 【常用】`#denyAll()` 方法，所有用户不可访问。
- 【常用】`#authenticated()` 方法，登录用户可访问。
- 【常用】`#hasRole(String role)` 方法， 拥有指定角色的用户可访问。
- 【常用】`#hasAnyRole(String... roles)` 方法，拥有指定任一角色的用户可访问。
- 【常用】`#hasAuthority(String authority)` 方法，拥有指定权限(`authority`)的用户可访问。



 Ant 风格路径表达式

| 通配符 | 说明                    |
| ------ | ----------------------- |
| ?      | 匹配任何单字符          |
| *      | 匹配0或者任意数量的字符 |
| **     | 匹配0或者更多的目录     |



### UserDetailsService

通过实现 UserDetailsService 接口实现用户认证。代码见 `spring-security-userdetail` 项目。

密码必须使用 PasswordEncoder 进行编码，否则会报错。在 WebSecurityConfigurer 中注入 `PasswordEncoder` 。

## 4、FAQ

1、Spring Security 主要解决什么问题？

答：认证和授权。



2、实际应用中我们如何实现 Spring Security 提供的功能？

答： 通过重写 `WebSecurityConfigurerAdapter` 的`configure(AuthenticationManagerBuilder auth)`方法 与 `onfigure(HttpSecurity http)` 方法分别实现认证管理器和 URL 的权限控制。



3、如何基于数据库来实现认证的用户信息的读取？

答：通过调用 `AuthenticationManagerBuilder#userDetailsService(userDetailsService)`

方法，使用自定义的 UserDetailService 实现类来实现用户信息的读取。