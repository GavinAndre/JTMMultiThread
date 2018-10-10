package com.gavinandre;

import java.util.concurrent.*;

public class ExecutorDemo {

    private static final int MAX = 10;

    public static void main(String[] args) {
        try {
            // fixedThreadPool(3);
            // newCachedThreadPool();
            // scheduledThreadPool();
            singleThreadExecutor();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 效率底下的斐波那契数列, 耗时的操作
    private static int fibc(int num) {
        if (num == 0) {
            return 0;
        }
        if (num == 1) {
            return 1;
        }
        return fibc(num - 1) + fibc(num - 2);
    }

    private static void fixedThreadPool(int size) throws CancellationException, ExecutionException,
            InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(size);
        for (int i = 0; i < MAX; i++) {
            Future<Integer> task = executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    System.out.println("执行线程 : " + Thread.currentThread().getName());
                    return fibc(20);
                }
            });
            System.out.println("第 " + i + "次计算,结果 : " + task.get());
        }
    }

    private static void newCachedThreadPool() throws CancellationException,
            ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < MAX; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("执行线程 : " + Thread.currentThread().getName()
                            + ", 结果 : " + fibc(20));
                }
            });
        }
    }

    private static void scheduledThreadPool() throws CancellationException,
            ExecutionException, InterruptedException {
        // ScheduledExecutorService executorService =
        // Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
        // 参数2为第一次延迟的时间，参数3为执行周期
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread : " + Thread.currentThread().getName() + ", 定时计算 : ");
                System.out.println("结果 : " + fibc(30));
            }
        }, 1, 2, TimeUnit.SECONDS);
        // 参数2为第一次延迟的时间,参数3为执行周期
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread : " + Thread.currentThread().getName() + ", 定时计算2 : ");
                System.out.println("结果 : " + fibc(40));
            }
        }, 1, 2, TimeUnit.SECONDS);
    }

    private static void singleThreadExecutor() throws CancellationException,
            ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < MAX; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("执行线程 : " + Thread.currentThread().getName()
                            + ", 结果 : " + fibc(20));
                }
            });
        }
    }
}
