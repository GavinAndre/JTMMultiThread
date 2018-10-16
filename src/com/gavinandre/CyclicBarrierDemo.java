package com.gavinandre;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {

    private static int BARRIER_SIZE = 5;
    private static int THREAD_SIZE = 10;
    private static CyclicBarrier mCyclicBarrier;

    public static void main(String[] args) {

        mCyclicBarrier = new CyclicBarrier(BARRIER_SIZE, new Runnable() {

            @Override
            public void run() {
                System.out.println(" ---> 满足条件,执行特定操作。 参与者: " + mCyclicBarrier.getParties());
            }
        });

        // 新建5个任务
        for (int i = 0; i < THREAD_SIZE; i++) {
            new WorkerThread().start();
        }
    }

    static class WorkerThread extends Thread {

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " 等待 CyclicBarrier.");

                // 将mCyclicBarrier的参与者数量加1
                mCyclicBarrier.await();
                // mCyclicBarrier的参与者数量等于5时，才继续往后执行
                System.out.println(Thread.currentThread().getName() + " 继续执行.");
            } catch (BrokenBarrierException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
