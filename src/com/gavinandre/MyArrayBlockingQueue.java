package com.gavinandre;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyArrayBlockingQueue<T> {
    // 数据数组
    private final T[] items;
    // 锁
    private final Lock lock = new ReentrantLock();
    // 队满的条件
    private Condition notFull = lock.newCondition();
    // 队空条件
    private Condition notEmpty = lock.newCondition();
    // 头部索引
    private int head;
    // 尾部索引
    private int tail;
    // 数据的个数
    private int count;

    public MyArrayBlockingQueue(int maxSize) {
        items = (T[]) new Object[maxSize];
    }

    public MyArrayBlockingQueue() {
        this(10);
    }

    public void put(T t) {
        lock.lock();
        try {
            while (count == getCapacity()) {
                System.out.println("数据已满，等待");
                notFull.await();
            }
            items[tail] = t;
            if (++tail == getCapacity()) {
                System.out.println("tail" + tail);
                tail = 0;
            }
            ++count;
            notEmpty.signalAll(); // 唤醒等待数据的线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public T take() {
        lock.lock();
        try {
            while (count == 0) {
                System.out.println("还没有数据，请等待");
                notEmpty.await();
            }
            T ret = items[head];
            items[head] = null;
            if (++head == getCapacity()) {
                System.out.println("head" + head);
                head = 0;
            }
            --count;
            notFull.signalAll(); // 唤醒添加数据的线程
            return ret;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    public int getCapacity() {
        return items.length;
    }

    public int size() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        MyArrayBlockingQueue<Integer> aQueue = new MyArrayBlockingQueue<Integer>(2);
        aQueue.put(3);
        aQueue.put(24);
        aQueue.take();
        aQueue.put(2);
        aQueue.put(21);
        aQueue.take();

        for (int i = 0; i < 5; i++) {
            System.out.println(aQueue.take());
        }
    }

}