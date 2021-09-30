package cn.jzq.refactoring.chapter06;

/**
 * 内联函数
 * 如果函数只是对另一个函数的简单委托，那么可以使用内联手法，找出有用的间接层，去除无用的间接层。
 */
public class A62_InlineMethodApp {

    public int getLimit(int limit) {
        return isGtMax(limit) ? 100 : limit;
    }

    public boolean isGtMax(int i) {
        return i > 100;
    }

    public int getLimitUsingInline(int limit) {
//        return Math.min(limit, 100);
        return limit > 100 ? 100 : limit;
    }
}
