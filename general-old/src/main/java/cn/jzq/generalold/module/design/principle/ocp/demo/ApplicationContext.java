package cn.jzq.generalold.module.design.principle.ocp.demo;
import cn.jzq.generalold.module.design.principle.ocp.AlertRule;
import cn.jzq.generalold.module.design.principle.ocp.Notification;

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