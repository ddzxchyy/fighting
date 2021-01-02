package cn.jzq.generalold.module.design.principle.ocp.demo;


import cn.jzq.generalold.module.design.principle.ocp.AlertRule;
import cn.jzq.generalold.module.design.principle.ocp.Notification;

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