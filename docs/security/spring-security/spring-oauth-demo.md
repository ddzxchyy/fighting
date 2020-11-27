# Spring Security Oauth 实战

项目见 [spring-security-oauth](https://github.com/ddzxchyy/fighting/tree/master/spring-security/spring-security-oauth)

## 1. 概述

使用 Spring Security Oauth，主要分为如下三个步骤

> - 配置资源服务器
> - 配置认证服务器
> - 配置spring security

oauth2 有四种模式，本文针对客户端模式。

> - 授权码模式（authorization code）
> - 简化模式（implicit）
> - 密码模式（resource owner password credentials）
> - 客户端模式（client credentials）



## 2. 项目依赖

```xml
<dependencies>
      <!-- 返回值，lombok，hutool 等依赖 -->
    <dependency>
        <groupId>cn.jzq</groupId>
        <artifactId>fighting-common</artifactId>
        <version>1.0.0</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.security.oauth</groupId>
        <artifactId>spring-security-oauth2</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
    <groupId>org.aspectj</groupId>
  	  <artifactId>aspectjrt</artifactId>
	   <version>1.9.6</version>
    </dependency>
<dependencies>
```



## 3. 配置认证服务器

client模式，没有用户的概念，直接与认证服务器交互，用配置中的客户端信息去申请 accessToken，客户端有自己的 client_id, client_secret 对应于用户的 username, password，而客户端也拥有自己的authorities，当采取 client 模式认证时，对应的权限也就是客户端自己的 authorities。

本项目采用了内存加载客户端的形式，便于演示。

```java
/**
 * 认证服务器
 */
@Configuration
@AllArgsConstructor
@EnableAuthorizationServer
public class Oauth2AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final RedisConnectionFactory redisConnectionFactory;
    private final AuthenticationManager authenticationManager;
    private final WebResponseExceptionTranslator webResponseExceptionTranslator;
    private final AuthenticationEntryPoint authenticationEntryPoint;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //配置两个客户端,一个用于password认证一个用于client认证
        clients.inMemory().withClient("client_1")
                .resourceIds(DEMO_RESOURCE_ID)
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .scopes("select")
                .authorities("client")
                .secret("123456")
                .and().withClient("client_2")
                .resourceIds(DEMO_RESOURCE_ID)
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("select")
                .authorities("client")
                .secret("123456");
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        CustomClientCredentialsTokenEndpointFilter endpointFilter = new CustomClientCredentialsTokenEndpointFilter(security);
        endpointFilter.afterPropertiesSet();
        endpointFilter.setAuthenticationEntryPoint(authenticationEntryPoint);
        security.addTokenEndpointAuthenticationFilter(endpointFilter);
        security.authenticationEntryPoint(authenticationEntryPoint)
                .tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST).tokenStore(tokenStore())
                .tokenServices(tokenService())
//                .tokenEnhancer(tokenEnhancer()).userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager).reuseRefreshTokens(false)
//                .pathMapping("/oauth/confirm_access", "/token/confirm_access")
                // 自定义异常处理
                .exceptionTranslator(webResponseExceptionTranslator);

    }


    @Bean
    public TokenStore tokenStore() {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
//        tokenStore.setPrefix("jzq");
        return tokenStore;
    }

    @Bean
    public OauthDefaultTokenServices tokenService() {
       OauthDefaultTokenServices  tokenServices = new OauthDefaultTokenServices();
        //配置token存储
        tokenServices.setTokenStore(tokenStore());
        //开启支持refresh_token，此处如果之前没有配置，启动服务后再配置重启服务，可能会导致不返回token的问题，解决方式：清除redis对应token存储
        tokenServices.setSupportRefreshToken(true);
        //复用 refresh_token
        tokenServices.setReuseRefreshToken(true);
        //token有效期，设置 6小时
        tokenServices.setAccessTokenValiditySeconds(6 * 60 * 60);
        //refresh_token有效期，设置一周
        tokenServices.setRefreshTokenValiditySeconds(7 * 24 * 60 * 60);
        return tokenServices;
    }


    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            final Map<String, Object> additionalInfo = new HashMap<>(4);
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }
}
```



## 4. 配置资源服务器



```java
/**
 * 资源服务器
 */
@Configuration
@EnableResourceServer
public class Oauth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String DEMO_RESOURCE_ID = "order";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 定义异常转换类生效
        AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        ((OAuth2AuthenticationEntryPoint) authenticationEntryPoint).setExceptionTranslator(new UserOAuth2WebResponseExceptionTranslator());
        resources.authenticationEntryPoint(authenticationEntryPoint)
                .resourceId(DEMO_RESOURCE_ID).stateless(true);
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers().anyRequest()
                .and()
                .anonymous()
                .and()
                .authorizeRequests()
//                    .antMatchers("/product/**").access("#oauth2.hasScope('select') and hasRole('ROLE_USER')")
                .antMatchers("/order/**").authenticated();//配置order访问控制，必须认证过后才可以访问
        // @formatter:on
    }
}


```



## 5. 配置 spring security

加载用户

```java
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        //Spring Security应该忽略URLS以xxx开头的路由
//        web.ignoring().antMatchers("/oauth/**");
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //String psd = encoder.encode("123456");
        String psd = "123456";
        manager.createUser(User.withUsername("user_1").password(psd).authorities("USER").build());
        manager.createUser(User.withUsername("user_2").password(psd).authorities("USER").build());
        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .requestMatchers().anyRequest()
            .and()
                .authorizeRequests()
                .antMatchers("/oauth/*").permitAll();
        // @formatter:on
    }

    @Bean
    @Override
    @SneakyThrows
    public AuthenticationManager authenticationManagerBean() {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
```





## 6.  资源服务器异常处理

实现 WebResponseExceptionTranslator 接口。

AuthenticationEntryPoint 只能处理资源服务器的异常。认证异常请见下文。

### 6.1 WebResponseExceptionTranslator

```java
/**
 * 资源服务器异常自定义捕获
 * 参考默认实现 DefaultWebResponseExceptionTranslator
 */
@Component
public class UserOAuth2WebResponseExceptionTranslator implements WebResponseExceptionTranslator {
    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        Throwable[] causeChain = this.throwableAnalyzer.determineCauseChain(e);
        Exception ase = (OAuth2Exception) this.throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
        //异常链中有OAuth2Exception异常
        if (ase != null) {
            return this.handleOAuth2Exception((OAuth2Exception) ase);
        }
        //身份验证相关异常
        ase = (AuthenticationException) this.throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, causeChain);
        if (ase != null) {
            return this.handleOAuth2Exception(new UnauthorizedException(e.getMessage(), e));
        }
        //异常链中包含拒绝访问异常
        ase = (AccessDeniedException) this.throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
        if (ase instanceof AccessDeniedException) {
            return this.handleOAuth2Exception(new ForbiddenException(ase.getMessage(), ase));
        }
        //异常链中包含Http方法请求异常
        ase = (HttpRequestMethodNotSupportedException) this.throwableAnalyzer.getFirstThrowableOfType(HttpRequestMethodNotSupportedException.class, causeChain);
        if (ase instanceof HttpRequestMethodNotSupportedException) {
            return this.handleOAuth2Exception(new MethodNotAllowed(ase.getMessage(), ase));
        }
        return this.handleOAuth2Exception(new ServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e));
    }

    private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) throws IOException {
        int status = e.getHttpErrorCode();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        if (status == HttpStatus.UNAUTHORIZED.value() || e instanceof InsufficientScopeException) {
            headers.set("WWW-Authenticate", String.format("%s %s", "Bearer", e.getSummary()));
        }
        UserOAuth2Exception exception = new UserOAuth2Exception(e.getMessage(), e);
        ResponseEntity<OAuth2Exception> response = new ResponseEntity<>(exception, headers, HttpStatus.valueOf(200));
        return response;
    }


    private static class MethodNotAllowed extends OAuth2Exception {
        public MethodNotAllowed(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return "method_not_allowed";
        }

        @Override
        public int getHttpErrorCode() {
            return 405;
        }
    }

    private static class UnauthorizedException extends OAuth2Exception {
        public UnauthorizedException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return "unauthorized";
        }

        @Override
        public int getHttpErrorCode() {
            return 401;
        }
    }

    private static class ServerErrorException extends OAuth2Exception {
        public ServerErrorException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return "server_error";
        }

        @Override
        public int getHttpErrorCode() {
            return 500;
        }
    }

    private static class ForbiddenException extends OAuth2Exception {
        public ForbiddenException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return "access_denied";
        }

        @Override
        public int getHttpErrorCode() {
            return 403;
        }
    }
}

```



### **6.2 异常类**

```java


@JsonSerialize(using = UserOAuth2ExceptionSerializer.class)
public class UserOAuth2Exception extends OAuth2Exception {
    private Integer status = 400;

    public UserOAuth2Exception(String message, Throwable t) {
        super(message, t);
        status = ((OAuth2Exception)t).getHttpErrorCode();
    }

    @Override
    public int getHttpErrorCode() {
        return status;
    }

}

```



### 6.3 序列化异常类

可以返回自定义的格式，如将 status 改写为 code

```java
/**
 * 序列化异常类
 * 此处可以改写 status 为 code
 * @author jzq
 */
public class UserOAuth2ExceptionSerializer extends StdSerializer<UserOAuth2Exception> {

    protected UserOAuth2ExceptionSerializer() {
        super(UserOAuth2Exception.class);
    }

    @Override
    public void serialize(UserOAuth2Exception e, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {
        generator.writeStartObject();
        generator.writeObjectField("code", e.getHttpErrorCode());
        String message = e.getMessage();
        if (message != null) {
            message = HtmlUtils.htmlEscape(message);
        }
        generator.writeStringField("msg", message);
        if (e.getAdditionalInformation() != null) {
            for (Map.Entry<String, String> entry : e.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                generator.writeStringField(key, add);
            }
        }
        generator.writeEndObject();
    }
}
```



### **6.4 认证入口**

它在用户请求处理过程中遇到认证异常时，被`ExceptionTranslationFilter`用于开启特定认证方案(`authentication schema`)的认证流程。

```java
@Slf4j
@Component
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Spring Security 异常", authException);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(R.error(401, authException.getLocalizedMessage()));
    }
}
```



### 6.5 修改资源服务器配置

```java
@Override
public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    // 定义异常转换类生效
    AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
    ((OAuth2AuthenticationEntryPoint) authenticationEntryPoint).setExceptionTranslator(new UserOAuth2WebResponseExceptionTranslator());
    resources.authenticationEntryPoint(authenticationEntryPoint)
            .resourceId(DEMO_RESOURCE_ID).stateless(true);
}
```



## 7.  认证服务器异常处理

[原文](https://www.it610.com/article/1296538405567537152.htm)

默认情况下申请令牌访问`oauth/token`未携带`client_secret`参数时会返回`Bad client credentials`。如果直接通过`AuthenticationEntryPoint`是无法自定义返回的信息，我们需要重写过滤器`ClientCredentialsTokenEndpointFilter`。

### **7.1 自定义过滤器**

```java
public class CustomClientCredentialsTokenEndpointFilter extends ClientCredentialsTokenEndpointFilter {

    private AuthorizationServerSecurityConfigurer configurer;
    private AuthenticationEntryPoint authenticationEntryPoint;

    public CustomClientCredentialsTokenEndpointFilter(AuthorizationServerSecurityConfigurer configurer) {
        this.configurer = configurer;
    }

    @Override
    public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
        // 把父类的干掉
        super.setAuthenticationEntryPoint(null);
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected AuthenticationManager getAuthenticationManager() {
        return configurer.and().getSharedObject(AuthenticationManager.class);
    }

    @Override
    public void afterPropertiesSet() {
        setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                authenticationEntryPoint.commence(httpServletRequest, httpServletResponse, e);
            }
        });
        setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                // 无操作-仅允许过滤器链继续到令牌端点
            }
        });
    }

}

```


### **7.2 认证入口**

同上文的认证入口



### **7.3 修改认证服务器配置**

```java
@Override
public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    CustomClientCredentialsTokenEndpointFilter endpointFilter = new CustomClientCredentialsTokenEndpointFilter(security);
    endpointFilter.afterPropertiesSet();
    endpointFilter.setAuthenticationEntryPoint(authenticationEntryPoint);
    security.addTokenEndpointAuthenticationFilter(endpointFilter);
    security.authenticationEntryPoint(authenticationEntryPoint)
            .tokenKeyAccess("isAuthenticated()")
            .checkTokenAccess("permitAll()");
}
```



## 8. 自定义 token 生成

[原文](https://www.cnblogs.com/panchanggui/p/12395451.html)

参考源码 `DefaultTokenServices`，对 `createAccessToken`方法进行改造，使每次访问产生的 token 都不相同，让上一个 token 在一段时间内有效

### 8.1  DefaultTokenServices 实现


```java
 @Transactional
public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {

        OAuth2AccessToken existingAccessToken = tokenStore.getAccessToken(authentication);
        OAuth2RefreshToken refreshToken = null;
        if (existingAccessToken != null) {
            if (existingAccessToken.isExpired()) {
                if (existingAccessToken.getRefreshToken() != null) {
                    refreshToken = existingAccessToken.getRefreshToken();
                    // The token store could remove the refresh token when the
                    // access token is removed, but we want to
                    // be sure...
                    tokenStore.removeRefreshToken(refreshToken);
                }
                tokenStore.removeAccessToken(existingAccessToken);
            } else {
                // modified by pancg 2020-03-02
                // 根据当前开放平台功能设计调整为每次获取新token并让老token在5分钟内有效

                // 1.这里是原来的实现
                // Re-store the access token in case the authentication has changed
                //tokenStore.storeAccessToken(existingAccessToken, authentication);
                //return existingAccessToken;

                // 2.让原token在token更新后5分钟内仍有效
                // Re-store the access token in case the authentication has changed
                tokenStore.storeAccessToken(createAccessTokenInNextMinute(existingAccessToken, authentication), authentication);

                // 3.下面是创建新token并返回
                // Only create a new refresh token if there wasn't an existing one
                // associated with an expired access token.
                // Clients might be holding existing refresh tokens, so we re-use it in
                // the case that the old access token
                // expired.
                if (refreshToken == null) {
                    refreshToken = createRefreshToken(authentication);
                }
                // But the refresh token itself might need to be re-issued if it has
                // expired.
                else if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
                    ExpiringOAuth2RefreshToken expiring = (ExpiringOAuth2RefreshToken) refreshToken;
                    if (System.currentTimeMillis() > expiring.getExpiration().getTime()) {
                        refreshToken = createRefreshToken(authentication);
                    }
                }
                OAuth2AccessToken accessToken = createAccessToken(authentication, refreshToken);
                tokenStore.storeAccessToken(accessToken, authentication);
                // In case it was modified
                refreshToken = accessToken.getRefreshToken();
                if (refreshToken != null) {
                    tokenStore.storeRefreshToken(refreshToken, authentication);
                }
                return accessToken;
            }
        }

        // Only create a new refresh token if there wasn't an existing one
        // associated with an expired access token.
        // Clients might be holding existing refresh tokens, so we re-use it in
        // the case that the old access token
        // expired.
        if (refreshToken == null) {
            refreshToken = createRefreshToken(authentication);
        }
        // But the refresh token itself might need to be re-issued if it has
        // expired.
        else if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
            ExpiringOAuth2RefreshToken expiring = (ExpiringOAuth2RefreshToken) refreshToken;
            if (System.currentTimeMillis() > expiring.getExpiration().getTime()) {
                refreshToken = createRefreshToken(authentication);
            }
        }

        OAuth2AccessToken accessToken = createAccessToken(authentication, refreshToken);
        tokenStore.storeAccessToken(accessToken, authentication);
        // In case it was modified
        refreshToken = accessToken.getRefreshToken();
        if (refreshToken != null) {
            tokenStore.storeRefreshToken(refreshToken, authentication);
        }
        return accessToken;

    }

private OAuth2AccessToken createAccessTokenInNextMinute(OAuth2AccessToken existingAccessToken, OAuth2Authentication authentication) {
        final int validitySeconds = 1 * 60;
        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(existingAccessToken.getValue());
        //DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(UUID.randomUUID().toString());
        //int validitySeconds = getAccessTokenValiditySeconds(authentication.getOAuth2Request());
        if (validitySeconds > 0) {
            token.setExpiration(new Date(System.currentTimeMillis() + (validitySeconds * 1000L)));
        }
        token.setRefreshToken(existingAccessToken.getRefreshToken());
        token.setScope(authentication.getOAuth2Request().getScope());

        //return accessTokenEnhancer != null ? accessTokenEnhancer.enhance(token, authentication) : token;
        return token;
    }

// 其余方法略

```



### 8.2 注入 TokenService

```java
@Bean
public OauthDefaultTokenServices tokenService() {
   OauthDefaultTokenServices  tokenServices = new OauthDefaultTokenServices();
    //配置token存储
    tokenServices.setTokenStore(tokenStore());
    //开启支持refresh_token，此处如果之前没有配置，启动服务后再配置重启服务，可能会导致不返回token的问题，解决方式：清除redis对应token存储
    tokenServices.setSupportRefreshToken(true);
    //复用 refresh_token
    tokenServices.setReuseRefreshToken(true);
    //token有效期，设置 6小时
    tokenServices.setAccessTokenValiditySeconds(6 * 60 * 60);
    //refresh_token有效期，设置一周
   // tokenServices.setRefreshTokenValiditySeconds(7 * 24 * 60 * 60);
    return tokenServices;
}
```

### 8.3 修改认证服务器配置

```java
  @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST).tokenStore(tokenStore())
                .tokenServices(tokenService());

    }
```





