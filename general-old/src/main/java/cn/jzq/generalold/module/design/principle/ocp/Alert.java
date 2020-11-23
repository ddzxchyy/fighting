package cn.jzq.generalold.module.design.principle.ocp;

import lombok.AllArgsConstructor;
import lombok.Data;

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
            notification.doNotify(NotificationEmergencyLevel.URGENCY, " TPS 警报");
        }
        if (errorCount > rule.getMatchedRule(api).getMaxErrorCount()) {
            notification.doNotify(NotificationEmergencyLevel.SEVERE, "请求失败数警报");
        }
    }
}
