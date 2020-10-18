# Spring OAuth2

原创出处 http://www.iocoder.cn/Spring-Security/OAuth2-learning/?vip

## OAuth 是什么

[OAuth](http://en.wikipedia.org/wiki/OAuth)是一个关于授权（authorization）的开放网络标准。

## OAuth 有什么用

OAuth 允许用户提供一个**令牌**，而不是用户名和密码来访问他们存放在特定服务提供者的数据。



## OAuth2.0 角色

在 OAuth2.0 中，有如下角色：

① **Authorization** Server：**认证**服务器，用于认证用户。如果客户端认证通过，则发放访问资源服务器的**令牌**。

② **Resource** Server：**资源**服务器，拥有受保护资源。如果请求包含正确的**访问令牌**，则可以访问资源。

> 友情提示：提供管理后台、客户端 API 的服务，都可以认为是 Resource Server。

③ **Client**：客户端。它请求资源服务器时，会带上**访问令牌**，从而成功访问资源。

> 友情提示：Client 可以是浏览器、客户端，也可以是内部服务。

④ Resource **Owner**：资源拥有者。最终用户，他有访问资源的**账号**与**密码**。

> 友情提示：可以简单把 Resource Owner 理解成人，她在使用 Client 访问资源。



## 快速入门

### 授权服务器

通过 Spring Security 提供认证功能

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                // 使用内存中的 InMemoryUserDetailsManager
                inMemoryAuthentication()
                // 不使用 PasswordEncoder 密码编码器
                .passwordEncoder(passwordEncoder())
                // 配置 yunai 用户
                .withUser("mrx").password("mrx").roles("USER");
    }

}
```



授权服务器

```java
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 用户认证 Manager
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory() // <4.1>
                .withClient("clientapp").secret("112233") // <4.2> Client 账号、密码。
                .authorizedGrantTypes("password") // <4.2> 密码模式
                .scopes("read_userinfo", "read_contacts") // <4.2> 可授权的 Scope
//                .and().withClient() // <4.3> 可以继续配置新的 Client
                ;
    }

}
```

① 在类上添加 [`@EnableAuthorizationServer`](https://github.com/spring-projects/spring-security-oauth/blob/master/spring-security-oauth2/src/main/java/org/springframework/security/oauth2/config/annotation/web/configuration/EnableAuthorizationServer.java) 注解，声明开启 OAuth **授权**服务器的功能。

同时，继承 [AuthorizationServerConfigurerAdapter](https://github.com/spring-projects/spring-security-oauth/blob/master/spring-security-oauth2/src/main/java/org/springframework/security/oauth2/config/annotation/web/configuration/AuthorizationServerConfigurerAdapter.java) 类，进行 OAuth **授权**服务器的配置。

② `#configure(AuthorizationServerEndpointsConfigurer endpoints)` 方法，配置使用的 AuthenticationManager 实现**用户认证**的功能。其中，`authenticationManager` 是由[「 SecurityConfig」](http://www.iocoder.cn/Spring-Security/OAuth2-learning/?vip#)创建 的 Spring Security 配置类。

③ `#configure(AuthorizationServerSecurityConfigurer oauthServer)` 方法，设置 `/oauth/check_token` 端点，通过认证后可访问。

> 友情提示：这里的认证，指的是使用 `client-id` + `client-secret` 进行的**客户端**认证，不要和**用户**认证混淆。

其中，`/oauth/check_token` 端点对应 [CheckTokenEndpoint](https://github.com/spring-projects/spring-security-oauth/blob/master/spring-security-oauth2/src/main/java/org/springframework/security/oauth2/provider/endpoint/CheckTokenEndpoint.java) 类，用于校验访问令牌的有效性。

- 在客户端访问资源服务器时，会在请求中带上**访问令牌**。
- 在资源服务器收到客户端的请求时，会使用请求中的**访问令牌**，找授权服务器确认该**访问令牌**的有效性。



### 为什么需要 clientid

通过 `client-id` 和 `client-secret`，授权服务器可以知道调用的来源以及正确性。这样，即使“坏人”拿到 Access Token ，但是没有 `client-id` 编号和 `client-secret`，也不能和授权服务器发生**有效**的交互。



## 资源服务器

gateway 不必做为资源服务器