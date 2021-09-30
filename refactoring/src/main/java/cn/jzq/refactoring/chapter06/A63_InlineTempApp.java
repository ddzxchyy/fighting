package cn.jzq.refactoring.chapter06;

import java.util.HashMap;
import java.util.Map;

/**
 * 内联临时变量
 */
public class A63_InlineTempApp {


    public Double getOrderPrice(Long price) {
        Map<String, Object> orderMap = getOrderMap(price);
        Long fen = (Long) orderMap.get("price");
        return fen / 100.0;
    }


    public Double getOrderPriceUsingLineTemp(Long price) {
        Map<String, Object> orderMap = getOrderMap(price);
        return ((Long) orderMap.get("price")) / 100.0;
    }

    private Double getOrderPriceYuan(Map<String, Object> map) {
        return (Double) map.get("price") / 100.0;
    }

    private Map<String, Object> getOrderMap(Long price) {
        Map<String, Object> orderMap = new HashMap<>(4);
        orderMap.put("price", price);
        orderMap.put("id", "123456789");
        return orderMap;
    }
}
