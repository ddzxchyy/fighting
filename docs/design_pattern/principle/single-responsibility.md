# 单一职责

A class or module should have a single responsibility.
一个类或者模块只负责完成一个职责。



## 目的

实现代码高内聚、低耦合，提高代码的复用性、可读性、可维护性。

避免将不相关的功能耦合在一起，来提高类的内聚性。



## 如何做

一个类只负责完成一个职责或者功能。不要设计大而全的类，要设计粒度小、功能单一的类。如果过分拆分，反而会降低内聚性。



## 如何判断类的职责是否足够单一

不同的应用场景、不同阶段的需求背景、不同的业务层面，对同一个类的职责是否单一，可能会有不同的判定结果。

实际上，一些侧面的判断指标更具有指导意义和可执行性，比如，出现下面这些情况就有可能说明这类的设计不满足单一职责原则：

- 类中的代码行数、函数或者属性过多
- 类依赖的其他类过多，或者依赖类的其他类过多
- 私有方法过多
- 比较难给类起一个合适的名字
- 类中大量的方法都是集中操作类中的某几个属性。



## 示例

```java
@Data
public class UserInfo {
    private long userId;
    private String username;
    private String email;
    private String telephone;
    private long createTime;
    private long lastLoginTime;
    private String avatarUrl;
    private String provinceOfAddress;
    private String cityOfAddress;
    private String regionOfAddress;
    private String detailedAddress;
}
```

UserInfo 类是否满足单一职责原则呢？一种观点是，UserInfo 类包含的都是用户相关的信息，所有的属性都属于用户这样一个业务模型，满足单一职责原则；另一种观点是地址信息所占的比重较高，可以继续拆分出 UserAddress 类。

哪种观点更对呢？实际上，要从中做出选择，我们不能脱离具体的应用场景。如果在这个社交产品中，用户的地址信息跟其他信息一样，只是单纯地用来展示，那 UserInfo 现在的设计就是合理的。但是，如果这个社交产品发展得比较好，之后又在产品中添加了电商的模块，用户的地址信息还会用在电商物流中，那我们最好将地址信息从 UserInfo 中拆分出来，独立成用户物流信息（或者叫地址信息、收货信息等）。

我们再进一步延伸一下。如果做这个社交产品的公司发展得越来越好，公司内部又开发出了跟多其他产品（可以理解为其他 App）。公司希望支持统一账号系统，也就是用户一个账号可以在公司内部的所有产品中登录。这个时候，我们就需要继续对 UserInfo 进行拆分，将跟身份认证相关的信息（比如，email、telephone 等）抽取成独立的类。

实际上，在真正的软件开发中，我们也没必要过于未雨绸缪，过度设计。所以，**我们可以先写一个粗粒度的类，满足业务需求。随着业务的发展，如果粗粒度的类越来越庞大，代码越来越多，这个时候，我们就可以将这个粗粒度的类，拆分成几个更细粒度的类。这就是所谓的持续重构**。