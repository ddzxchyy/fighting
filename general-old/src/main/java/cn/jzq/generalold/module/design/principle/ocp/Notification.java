package cn.jzq.generalold.module.design.principle.ocp;

import lombok.extern.slf4j.Slf4j;

/**
 * @author by jzq
 * @date 2019/12/12
 */
@Slf4j
public class Notification {

    public void doNotify(String level, String msg) {
        log.info("level: {}, msg: {}", level, msg);
    }
}
