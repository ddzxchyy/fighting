# 注解



## 常用注解

### @Configuration 

spring 容器在启动时有个专门处理 @Configuration 的类，会对 @Configuration 修饰的类 cglib 动态代理进行增强，所以需要满足一下条件

- 配置类必须以类的形式提供（不能是工厂方法返回的实例），允许通过生成子类在运行时增强（cglib 动态代理）。
- 配置类不能是 `final` 类（没法动态代理）。
- 配置注解通常为了通过 `@Bean` 注解生成 Spring 容器管理的类，
- 配置类必须是非本地的（即不能在方法中声明，不能是 private）。
- 任何嵌套配置类都必须声明为`static`。
- `@Bean` 方法可能不会反过来创建进一步的配置类（也就是返回的 bean 如果带有 `@Configuration`，也不会被特殊处理，只会作为普通的 bean）



@Configuration 注解生成的 Bean 都是同一个。