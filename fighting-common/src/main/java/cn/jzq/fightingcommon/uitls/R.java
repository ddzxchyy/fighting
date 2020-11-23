/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package cn.jzq.fightingcommon.uitls;


import cn.hutool.json.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * 返回数据
 */
@Data
public class R implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String msg;

    private Object data;

    private static JSONObject emptyJson = new JSONObject();


    public static R error() {
        return error(500, "服务器开小差了");
    }

    public static R error(String msg) {
        return error(500, msg);
    }

    public static R fail(String msg) {
        return error(5000, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.setCode(code);
        r.setMsg(new String(msg.getBytes(), StandardCharsets.UTF_8));
        r.setData(emptyJson);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.setCode(0);
        r.setMsg(msg);
        return r;
    }

    public static R ok(Object obj) {
        R r = new R();
        r.setCode(0);
        r.setMsg("success");
        r.setData(obj);
        return r;
    }

    public static R ok() {
        return new R();
    }


    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("data", data);
        jsonObject.set("msg", msg);
        jsonObject.set("code", code);
        return jsonObject.toString();
    }
}
