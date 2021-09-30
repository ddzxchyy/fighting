package cn.jzq.refactoring.chapter06;

import java.util.Locale;

/**
 * 引入解释性变量
 */
public class A65_IntroduceExplainingVariableApp {

    public static void main(String[] args) {

        String browser = "WA_HA_HA".toUpperCase();
        if (browser.contains("IE6") && browser.contains("IE7")) {
            // doSomething
            System.out.println("please use chrome");
        }
        boolean isIE6 = browser.contains("IE6");
        boolean isIE7 = browser.contains("IE6");
        if (isIE6 && isIE7) {
            // doSomething
            System.out.println("please use chrome");
        }
    }
}
