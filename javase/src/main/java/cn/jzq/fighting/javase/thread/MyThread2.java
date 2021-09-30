package cn.jzq.fighting.javase.thread;

public class MyThread2 implements Runnable {

    @Override
    public void run() {
        System.out.println("通过实现接口的方式实现线程");
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new MyThread2());
        thread.start();

        new Thread(() -> System.out.println("lambda 方式"))
                .start();
    }
}