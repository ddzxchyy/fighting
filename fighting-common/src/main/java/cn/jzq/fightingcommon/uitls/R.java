package cn.jzq.fightingcommon.uitls;

import cn.hutool.json.JSONObject;
import lombok.Data;

import java.io.Serializable;

/**
 * 请求返回对象
 * data 有参数的情况下请按 json 格式返回
 *
 * @author jzq
 * @date 2021-06-23
 */
@Data
@SuppressWarnings("all")
public class R<T> implements Serializable {

    /**
     * 0 成功, 500 失败
     *
     * @mock @pick(0,500)
     */
    private Integer code;

    /**
     * 成功: success, 失败: 错误信息
     */
    private String msg;

    private T data;

    public R() {
    }

    public R(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    public static <T> R ok() {
        return ok(new JSONObject());
    }

    public static <T> R ok(T t) {

        R<T> res = new R<>();
        res.setCode(0);
        res.setMsg("success");
        res.setData(t);
        return res;
    }


    public static R error(String msg) {
        R res = new R<>();
        res.setCode(500);
        res.setMsg(msg);
        return res;
    }

    public static <T> R error(String msg, T t) {
        R res = new R<>();
        res.setCode(500);
        res.setMsg(msg);
        res.setData(t);
        return res;
    }


    public static R error() {
        return error("未知异常，请联系管理员");
    }

}