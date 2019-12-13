package cn.jzq.xqg.module.design.principle.ocp.demo;

/**
 * @author by jzq
 * @date 2019/12/12
 */
public class OcpDemo2 {
    public static void main(String[] args) {
        ApiStatInfo apiStatInfo = new ApiStatInfo();
        apiStatInfo.setTimeoutCount(289);
        apiStatInfo.setDurationOfSeconds(1);
        apiStatInfo.setErrorCount(100);
        apiStatInfo.setRequestCount(1000);
        ApplicationContext context  = ApplicationContext.getInstance();
        OcpAlert alert = context.getAlert();
        alert.check(apiStatInfo);

        ApplicationContext context1 = ApplicationContext.getInstance();
        context1.getAlert();
    }
}
