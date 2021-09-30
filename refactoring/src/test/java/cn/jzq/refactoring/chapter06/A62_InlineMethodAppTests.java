package cn.jzq.refactoring.chapter06;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * 内联函数 测试
 */
public class A62_InlineMethodAppTests {

    @Test
    public void test() {

        // given
        A62_InlineMethodApp inlineMethodApp = new A62_InlineMethodApp();
        // when
        int limit1 = inlineMethodApp.getLimit(110);
        int limit2 = inlineMethodApp.getLimitUsingInline(110);
        int ltLimit = inlineMethodApp.getLimitUsingInline(90);
        int eqLimit = inlineMethodApp.getLimitUsingInline(100);
        int gtLimit = inlineMethodApp.getLimitUsingInline(110);
        // then
        Assert.assertEquals(limit1, limit2);
        Assert.assertEquals(90, ltLimit);
        Assert.assertEquals(100, eqLimit);
        Assert.assertEquals(100, gtLimit);
    }
}
