package cn.jzq.generalold.module.design.principle.ocp.demo;

import lombok.Data;

/**
 * check 参数封装
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