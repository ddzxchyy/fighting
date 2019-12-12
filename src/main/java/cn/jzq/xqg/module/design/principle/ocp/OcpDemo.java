package cn.jzq.xqg.module.design.principle.ocp;

/**
 * @author by jzq
 * @date 2019/12/12
 */
public class OcpDemo {
    public static void main(String[] args) {
        Alert alert = new Alert(new AlertRule(), new Notification());
        alert.check("test", 200L, 150L, 1);
    }
}
