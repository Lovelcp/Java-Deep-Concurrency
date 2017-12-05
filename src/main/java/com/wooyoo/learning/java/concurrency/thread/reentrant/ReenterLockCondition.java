package com.wooyoo.learning.java.concurrency.thread.reentrant;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReenterLockCondition implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition(); // 通过lock生成一个与之绑定的Condition对象

    @Override
    public void run() {
        try {
            lock.lock();
            condition.await();
            System.out.println("Thread is going on");
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLockCondition c = new ReenterLockCondition();
        Thread t1 = new Thread(c);
        t1.start();
        Thread.sleep(2000);

        lock.lock();
        // 通知线程t1继续执行
        condition.signal();
        System.out.println("Main thread signal condition");
        Thread.sleep(2000);
        // 即使调用了signal方法，也要等到main释放锁，t1才能继续执行
        lock.unlock();
    }
}
