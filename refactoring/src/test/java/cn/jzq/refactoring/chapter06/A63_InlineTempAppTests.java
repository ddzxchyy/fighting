package cn.jzq.refactoring.chapter06;

import org.junit.Assert;
import org.junit.Test;

public class A63_InlineTempAppTests {

    @Test
    public void test() {
        A63_InlineTempApp inlineTempApp = new A63_InlineTempApp();
        Double price = inlineTempApp.getOrderPriceUsingLineTemp(111L);
        Double p = 1.11;
        Assert.assertEquals(p, price);
    }
}
