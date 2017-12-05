package com.wooyoo.learning.java.concurrency.thread.locksupport;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo2 {
    public static Object u = new Object();

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (u) {
                System.out.println("in " + getName());
                LockSupport.park();
                if (Thread.interrupted()) {
                    System.out.println(getName() + " interrupted");
                }
                System.out.println(getName() + " finish");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ChangeObjectThread t1 = new ChangeObjectThread("t1");
        ChangeObjectThread t2 = new ChangeObjectThread("t2");

        t1.start();
        Thread.sleep(100);

        t2.start();

        t1.interrupt();
        LockSupport.unpark(t2);
    }

}
