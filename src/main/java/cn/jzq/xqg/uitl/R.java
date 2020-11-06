package cn.jzq.xqg.uitl;

import lombok.Data;

import java.util.HashMap;

@Data
public class R extends HashMap {

    private int code;

    private String msg;

    private Object data;

    public static R ok() {
        R r = new R();
        r.setCode(1);
        r.setMsg("success");
        return r;
    }
}
