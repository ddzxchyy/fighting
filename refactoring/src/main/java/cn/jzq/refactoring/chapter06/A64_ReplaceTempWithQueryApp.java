package cn.jzq.refactoring.chapter06;

/**
 * 以查询代替临时变量
 */
public class A64_ReplaceTempWithQueryApp {

    private final int quantity = 10;
    private final int itemPrice = 5;

    public double getPrice() {
        double basePrice = quantity * itemPrice;
        final double discountFactor;
        if (basePrice > 100) {
            discountFactor = 0.95;
        } else {
            discountFactor = 0.98;
        }
        return basePrice * discountFactor;
    }

    public double getPriceUsingQuery() {
        return basePrice() * discountFactor();
    }

    private double basePrice() {
        return quantity * itemPrice;
    }

    private double discountFactor() {
        if (basePrice() > 100) {
            return 0.95;
        }
        return 0.98;
    }
}
