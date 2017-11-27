package com.wooyoo.learning.java.concurrency.thread.reentrant;

import java.util.concurrent.locks.ReentrantLock;

public class ReenterLock implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();
    public static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 10000000; j++) {
            lock.lock();
            i++;
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLock obj = new ReenterLock();
        Thread t1 = new Thread(obj);
        Thread t2 = new Thread(obj);
        t1.start();
        t1.join();
        t2.start();
        t2.join();
        System.out.println(i);
    }
}
