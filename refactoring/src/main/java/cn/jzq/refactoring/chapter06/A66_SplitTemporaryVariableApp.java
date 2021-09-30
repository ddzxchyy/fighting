package cn.jzq.refactoring.chapter06;

/**
 * 分解临时变量
 */
public class A66_SplitTemporaryVariableApp {

    public static void main(String[] args) {
        int height = 10;
        int width = 8;
        double temp = 2 * (height + width);
        System.out.println(temp);
        temp = height * width;
        System.out.println(temp);

        final double perimeter = 2 * (height + width);
        final double area = height * width;
        System.out.println(perimeter);
        System.out.println(area);
    }


}
