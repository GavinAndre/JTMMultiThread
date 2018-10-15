package com.gavinandre;

public class SynchronizedDemo {

    public synchronized void syncMethod() {
        // 代码
    }

    public void syncThis() {
        synchronized (this) {
            // 代码
        }
    }

    public void syncClassMethod() {
        synchronized (SynchronizedDemo.class) {
            // 代码
        }
    }

    public synchronized static void syncStaticMethod() {
        // 代码
    }
}
