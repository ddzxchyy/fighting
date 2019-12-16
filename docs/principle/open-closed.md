# 开闭原则

对扩展开放，对修改关闭。

在不同的颗粒度下，同样的改动代码可能被认为扩展，也可能被认为修改。

## 意图

保持软件的扩展性。

## 为什么

修改原有代码，可能会破坏原有代码的正常运行，破坏单元测试。

## 如何做

要时刻具备扩展意识、抽象意识，封装意识。在写代码时，思考这段代码未来可能有哪些需求变更，事先预留扩展点，以便在未来需求变更的时候，在不改动代码整体结构的情况下，以最小的代码改动完成需求变更。

添加一个新功能，不可能任何代码都不修改，我们要做的是尽量保持核心的那部分逻辑满足开闭原则

## 方法

使用多态、依赖注入、基于接口编程、设置模式等。

## 示例

```java

public class Alert {
    private AlertRule rule;
    private Notification notification;

    public Alert(AlertRule rule, Notification notification) {
        this.rule = rule;
        this.notification = notification;
    }

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

