# Spring Cloud Gateway

文本参考引用文章：

[1. Spring Cloud GateWay 入门](http://www.ityouknow.com/springcloud/2018/12/12/spring-cloud-gateway-start.html)

[2. 跨域处理](https://blog.csdn.net/kwame211/article/details/107514290)

[3. 官网 docs](https://docs.spring.io/spring-cloud-gateway/docs/3.0.0-SNAPSHOT/reference/html/)



## 简介

spring cloud gateway 为微服务架构提供一种简单有效的统一的 API 路由管理方式，并且基于 Filter 链的方式提供了网关基本的功能，例如：安全，监控/指标，和限流。



**相关概念:**

- Route（路由）：这是网关的基本构建块。它由一个 ID，一个目标 URI，一组断言和一组过滤器定义。如果断言为真，则路由匹配。
- Predicate（断言）：这是一个 Java 8 的 Predicate。输入类型是一个 ServerWebExchange。我们可以使用它来匹配来自 HTTP 请求的任何内容，例如 headers 或参数。
- Filter（过滤器）：这是`org.springframework.cloud.gateway.filter.GatewayFilter`的实例，我们可以使用它修改请求和响应。

**工作流程：**

<img src="https://docs.spring.io/spring-cloud-gateway/docs/3.0.0-SNAPSHOT/reference/html/images/spring_cloud_gateway_diagram.png" />



客户端向 Spring Cloud Gateway 发出请求。如果 Gateway Handler Mapping 中找到与请求相匹配的路由，将其发送到 Gateway Web Handler。Handler 再通过指定的过滤器链来将请求发送到我们实际的服务执行业务逻辑，然后返回。 过滤器之间用虚线分开是因为过滤器可能会在发送代理请求之前（“pre”）或之后（“post”）执行业务逻辑。

## 路由规则

一、根据时间

```yml
predicates:
  - After=2018-01-20T06:06:06+08:00[Asia/Shanghai]
```





## 跨域处理

```yml
spring:
  cloud:
    gateway:
      filter:
        remove-hop-by-hop:
          headers:
          # 以下是去掉网关默认去掉的请求响应头
          - trailer
          - te
          - keep-alive
          - transfer-encoding
          - upgrade
          - proxy-authenticate
          - connection
          - proxy-authorization
          - x-application-context
          # 以下是去掉服务层面定义的跨域
          - access-control-allow-credentials
          - access-control-allow-headers
          - access-control-allow-methods
          - access-control-allow-origin
          - access-control-max-age
          - vary
      globalcors:
        corsConfigurations:
          '[/**]':
            allowCredentials: true
            allowedOrigins: "*"
            allowedHeaders: "*"
            allowedMethods: "*"
            maxAge: 3628800
```

