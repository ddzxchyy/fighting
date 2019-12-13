package cn.jzq.xqg.module.design.principle.ocp.demo;

import cn.jzq.xqg.module.design.principle.ocp.AlertRule;
import cn.jzq.xqg.module.design.principle.ocp.Notification;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 满足 开闭原则 的 Alert 类
 *
 * @author by jzq
 * @date 2019/12/12
 */
@Data
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
