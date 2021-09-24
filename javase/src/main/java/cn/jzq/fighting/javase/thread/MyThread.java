package cn.jzq.fighting.javase.thread;

public class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println("ha ha ha");
    }

    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start();;
    }
}
