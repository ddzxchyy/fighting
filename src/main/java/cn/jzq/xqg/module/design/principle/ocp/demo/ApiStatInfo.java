package cn.jzq.xqg.module.design.principle.ocp.demo;

import lombok.Data;

/**
 * check 入参封装
 * @author jzq
 */
@Data
public class ApiStatInfo {
    private String api;
    private long requestCount;
    private long errorCount;
    private long durationOfSeconds;
    /**
     * 改动一：添加新字段
     */
    private long timeoutCount;
}