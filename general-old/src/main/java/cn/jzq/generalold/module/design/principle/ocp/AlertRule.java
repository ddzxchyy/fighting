package cn.jzq.generalold.module.design.principle.ocp;

import cn.hutool.core.util.StrUtil;

/**
 * @author by jzq
 * @date 2019/12/12
 */
public class AlertRule {
    public Rule getMatchedRule(String api) {
        return new Rule();
    }
}
