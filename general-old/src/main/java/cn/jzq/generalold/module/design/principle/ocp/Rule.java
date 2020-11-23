package cn.jzq.generalold.module.design.principle.ocp;

import lombok.Data;
import lombok.Getter;

/**
 * @author by jzq
 * @date 2019/12/12
 */
@Getter
public class Rule {
    private Long maxTps = 100L;

    private Long maxErrorCount = 100L;
}
