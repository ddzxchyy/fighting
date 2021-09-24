package cn.jzq.fighting.javase;

import org.junit.jupiter.api.Test;

public class ThreadTests {
    @Test
    public void blockedTest() throws InterruptedException {

        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                testMethod();
            }
        }, "a");
        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                testMethod();
            }
        }, "b");

        a.start();
//        Thread.sleep(1000);
        a.join(1000);
        b.start();
        System.out.println(a.getName() + ":" + a.getState()); // 输出？
        System.out.println(b.getName() + ":" + b.getState()); // 输出？
    }

    // 同步方法争夺锁
    private synchronized void testMethod() {
        try {
            Thread.sleep(2000L);
//            System.out.println("123");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
