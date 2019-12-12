package cn.jzq.xqg.module.design.principle.ocp.demo;

import cn.jzq.xqg.module.design.principle.ocp.Alert;
import cn.jzq.xqg.module.design.principle.ocp.AlertRule;
import cn.jzq.xqg.module.design.principle.ocp.Notification;

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

    /**
     * 饿汉式单例
     */
    private static final ApplicationContext instance = new ApplicationContext();

    private ApplicationContext() {
        System.out.println(instance);
        instance.initializeBeans();
    }

    public static ApplicationContext getInstance() {
        return instance;
    }
}