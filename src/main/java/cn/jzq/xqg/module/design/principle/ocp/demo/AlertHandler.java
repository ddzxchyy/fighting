package cn.jzq.xqg.module.design.principle.ocp.demo;

import cn.jzq.xqg.module.design.principle.ocp.AlertRule;
import cn.jzq.xqg.module.design.principle.ocp.Notification;

/**
 * 警告类
 * @author jzq
 */
public abstract class AlertHandler {
    protected AlertRule rule;
    protected Notification notification;

    public AlertHandler(AlertRule rule, Notification notification) {
        this.rule = rule;
        this.notification = notification;
    }

    public abstract void check(ApiStatInfo apiStatInfo);
}