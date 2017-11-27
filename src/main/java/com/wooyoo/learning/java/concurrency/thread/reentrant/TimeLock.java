package com.wooyoo.learning.java.concurrency.thread.reentrant;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TimeLock implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                Thread.sleep(6000);
            }
            else {
                System.out.println("get lock failed");
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        TimeLock l = new TimeLock();
        Thread t1 = new Thread(l);
        Thread t2 = new Thread(l);
        t1.start();
        t2.start();
    }
}
