package cn.jzq.xqg.module.design.principle.ocp.demo;

import cn.jzq.xqg.module.design.principle.ocp.AlertRule;
import cn.jzq.xqg.module.design.principle.ocp.Notification;
import cn.jzq.xqg.module.design.principle.ocp.NotificationEmergencyLevel;

/**
 * tps 处理器
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