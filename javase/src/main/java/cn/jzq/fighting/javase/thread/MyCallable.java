package cn.jzq.fighting.javase.thread;

import java.util.concurrent.*;

/**
 * Callable一般是配合线程池工具ExecutorService来使用的。
 * Future 接口
 */
public class MyCallable implements  Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        Thread.sleep(1000);
        return 1;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        MyCallable callable = new MyCallable();
        Future<Integer> future = executorService.submit(callable);
        // 注意调用get方法会阻塞当前线程，直到得到结果。
        // 所以实际编码中建议使用可以设置超时时间的重载get方法。
        Integer result = future.get();
//        future.cancel(false);
        System.out.println(result);

        FutureTask<Integer> futureTask = new FutureTask<>(new MyCallable());
        executorService.submit(futureTask);
        System.out.println(futureTask.get());
    }

}
