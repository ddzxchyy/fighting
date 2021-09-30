package cn.jzq.refactoring.chapter06;

/**
 * 移除对参数的赋值
 * 会降低代码的清晰度，不熟悉 java 传递值方式的人会产生误解
 * java 是 call by value 的方式
 * 对基本类型赋值是不会改变实参的
 * 对对象赋值可以改变对象的值。如果让形参重新引用一个新的对象，那么实参和形参将不是同一个对象。
 */
public class A67_RemoveAssignmentToParametersApp {

    public int discount(int price, int days) {
        if (price > 50) {
            price -= 2;
        }
        // doSomething;
        return price;
    }

    public int discountUsingApp(int price, int days) {
        int result = price;
        if (price > 50) {
            result -= 2;
        }
        return result;
    }

    public static String test(String s) {
        String ss = s.replace("1", "2");
        System.out.println(s);
        s = "123";
        System.out.println(ss);
        return ss;
    }

    public static void main(String[] args) {
        String s = "1";
        System.out.println(s);
    }
}
