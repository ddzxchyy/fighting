# 拦截器

## 1. 简介

拦截器（Interceptor）同 Filter 过滤器一样，它俩都是面向切面编程——AOP 的具体实现（AOP切面编程只是一种编程思想而已）。



## 2. 快速入门

自定义 Interceptor 的话必须实现 **org.springframework.web.servlet.HandlerInterceptor**接口或继承 **org.springframework.web.servlet.handler.HandlerInterceptorAdapter**类，并且需要重写下面下面3个方法：

```java
default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		return true;
}

default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
}


default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {
}

```

!> ***preHandle***方法返回 **true**或 **false**。如果返回 **true**，则意味着请求将继续到达 **Controller** 被处理。



### 配置

配置拦截器

```java

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // LogInterceptor apply to all URLs.
        registry.addInterceptor(new LogInterceptor());

        // Old Login url, no longer use.
        // Use OldURLInterceptor to redirect to a new URL.
        registry.addInterceptor(new OldLoginInterceptor())//
                .addPathPatterns("/admin/oldLogin");


}
```

