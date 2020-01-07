package cn.jzq.xqg.module.design.principle.ocp.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * 满足 开闭原则 的 Alert 类
 *
 * @author by jzq
 * @date 2019/12/12
 */
public class OcpAlert {
    private List<AbstractAlertHandler> alertHandlers = new ArrayList<>();

    public void addAlertHandler(AbstractAlertHandler alertHandler) {
        this.alertHandlers.add(alertHandler);
    }

    public void check(ApiStatInfo apiStatInfo) {
        for (AbstractAlertHandler handler : alertHandlers) {
        handler.check(apiStatInfo);
        }
    }
}
