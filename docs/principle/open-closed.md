# 开闭原则

对扩展开放，对修改关闭。

在不同的颗粒度下，同样的改动代码可能被认为扩展，也可能被认为修改。

## 理解
### 目的

保持软件的扩展性。

### 为什么

修改原有代码，可能会破坏原有代码的正常运行，破坏单元测试。

### 如何做

要时刻具备扩展意识、抽象意识，封装意识。在写代码时，思考这段代码未来可能有哪些需求变更，事先预留扩展点，以便在未来需求变更的时候，在不改动代码整体结构的情况下，以最小的代码改动完成需求变更。

添加一个新功能，不可能任何代码都不修改，我们要做的是尽量保持核心的那部分逻辑满足开闭原则。

### 方法

使用多态、依赖注入、基于接口编程、设计模式等。

## 示例

下面是一段 API 报警代码
```java
/**
 * @author by jzq
 * @date 2019/12/12
 */
@Data
@AllArgsConstructor
public class Alert {
    private AlertRule rule;
    private Notification notification;

    /**
     * 当接口的 TPS 超过某个预先设置的最大值时，以及当接口请求出错数大于某个最大允许值时，
     * 就会触发告警，通知接口的相关负责人或者团队。
     *
     * @param api               请求路径
     * @param requestCount      请求数量
     * @param errorCount        请求错误数
     * @param durationOfSeconds 持续时间
     */
    public void check(String api, long requestCount, long errorCount, long durationOfSeconds) {
        long tps = requestCount / durationOfSeconds;
        if (tps > rule.getMatchedRule(api).getMaxTps()) {
            notification.doNotify(NotificationEmergencyLevel.URGENCY, " TPS 超标");
        }
        if (errorCount > rule.getMatchedRule(api).getMaxErrorCount()) {
            notification.doNotify(NotificationEmergencyLevel.SEVERE, "请求失败数超标");
        }
    }
}
```

现在需求要加一个新功能，当每秒钟接口超时请求个数，超过某个预先设置的最大阈值时，我们也要触发告警发送通知。

```java
/**
 * @author by jzq
 * @date 2019/12/12
 */
@Data
@AllArgsConstructor
public class Alert {
    private AlertRule rule;
    private Notification notification;

     // 改动一：添加形参 timeoutCount
    public void check(String api, long requestCount, long errorCount, 
    		long timeoutCount, long durationOfSeconds) {
        long tps = requestCount / durationOfSeconds;
        if (tps > rule.getMatchedRule(api).getMaxTps()) {
            notification.doNotify(NotificationEmergencyLevel.URGENCY, " TPS 警报");
        }
        if (errorCount > rule.getMatchedRule(api).getMaxErrorCount()) {
            notification.doNotify(NotificationEmergencyLevel.SEVERE, "请求失败数警报");
        }
        // 改动二：添加接口超时处理逻辑
        long timeoutTps = timeoutCount / durationOfSeconds;
        if (timeoutTps > rule.getMatchedRule(api).getMaxTimeoutTps()) {
            notification.doNotify(NotificationEmergencyLevel.URGENCY, "超时警报");
        }
    }
}
```

改动一使所有调用该接口的代码都得修改。改动二使相应的单元测验都需要修改。

上面的改动是基于 “修改” 来实现的，下面将基于 “扩展” 来改动。

- 针对改动一，将方法的形参封装成对象 ApiStatInfo。
- 针对改动二，引入 handler 的概念，将 if 逻辑 判断分散到各个 handler 中。

```java
/**
 * 满足开闭原则的 Alert 类
 *
 * @author by jzq
 * @date 2019/12/12
 */
public class OcpAlert {
    private List<AlertHandler> alertHandlers = new ArrayList<>();

    public void addAlertHandler(AlertHandler alertHandler) {
        this.alertHandlers.add(alertHandler);
    }

    public void check(ApiStatInfo apiStatInfo) {
        for (AlertHandler handler : alertHandlers) {
            handler.check(apiStatInfo);
        }
    }
}
```
```java
/**
 * 警告类
 * @author jzq
 */
public abstract class AbstractAlertHandler {
    protected AlertRule rule;
    protected Notification notification;

    public AbstractAlertHandler(AlertRule rule, Notification notification) {
        this.rule = rule;
        this.notification = notification;
    }

    /**
     * api 报警
     * @param apiStatInfo 参数
     */
    public abstract void check(ApiStatInfo apiStatInfo);
}
```
```java
/**
 * tps 报警处理器
 *
 * @author jzq
 */
public class TpsAlertHandler extends AlertHandler {
    public TpsAlertHandler(AlertRule rule, Notification notification) {
        super(rule, notification);
    }

    @Override
    public void check(ApiStatInfo apiStatInfo) {
        long tps = apiStatInfo.getRequestCount() / apiStatInfo.getDurationOfSeconds();
        if (tps > rule.getMatchedRule(apiStatInfo.getApi()).getMaxTps()) {
            notification.doNotify(NotificationEmergencyLevel.URGENCY, "...");
        }
    }
}
```
```java
/**
 * 负责 Alert 的创建、组装（alertRule 和 notification 的依赖注入）、初始化（添加 handlers）工作。
 *
 * @author jzq
 * @date 2019-12-12
 */
public class ApplicationContext {
    private AlertRule alertRule;
    private Notification notification;
    private OcpAlert alert;

    public void initializeBeans() {
        this.alertRule = new AlertRule();
        this.notification = new Notification();
        this.alert = new OcpAlert();
        this.alert.addAlertHandler(new TpsAlertHandler(alertRule, notification));
    }


    public OcpAlert getAlert() {
        return alert;
    }

    private static final ApplicationContext INSTANCE = new ApplicationContext();

    /**
     * 饿汉式单例
     */
    private ApplicationContext() {
    }

    public static ApplicationContext getInstance() {
        if (INSTANCE.getAlert() == null) {
            INSTANCE.initializeBeans();
        }
        return INSTANCE;
    }
}
```

基于上述代码实现超时报警功能，所需的改动：

1. 在 ApiStatInfo 中添加 timeoutCount 字段
2. 添加新的 handler 实现类
3. 在 context 类中注册新的 handler
4. 使用 Alert 类时，给实参 ApiStatInfo 设置 timeoutCount 的值。


重构之后的代码的有点：
- 添加新功能，不需要改动 check 函数的逻辑，不会造成调用方的修改。
- 不会破坏原有的单元测试，只需要添加新的单元测试，原来的单元测试都不会失败，也不需要修改。

## 示例分析
改动一，在类这一层面是 修改，在方法（及其属性）方面可以认为是 扩展。实际上，我们也没必要纠结某个代码改动是“修改”还是“扩展”，更没必要太纠结它是否违反“开闭原则”。我们回到这条原则的设计初衷：**只要它没有破坏原有的代码的正常运行，没有破坏原有的单元测试，我们就可以说，这是一个合格的代码改动。**

改动三和四，无论在层面都是修改。但是，有些修改是再说难免的，是可以接受的。在重构之后的 Alert 代码中，我们的核心逻辑集中在 Alert 类及其各个 handler 中，当我们在添加新的告警逻辑的时候，Alert 类完全不需要修改，而只需要扩展一个新 handler 类。如果我们把 Alert 类及各个 handler 类合起来看作一个“模块”，那模块本身在添加新的功能的时候，完全满足开闭原则。



## 总结

如果理解开闭原则？

如何做到开闭原则？

