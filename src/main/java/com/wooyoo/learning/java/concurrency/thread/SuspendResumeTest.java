package com.wooyoo.learning.java.concurrency.thread;

public class SuspendResumeTest {
    public static Object u = new Object();

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (u) {
                System.out.println("in " + getName());
                Thread.currentThread()
                      .suspend();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ChangeObjectThread t1 = new ChangeObjectThread("t1");
        ChangeObjectThread t2 = new ChangeObjectThread("t2");

        t1.start();
        Thread.sleep(100);

        t2.start();

        t1.resume();
        t2.resume(); // resume发生在suspend之前，导致suspend一直无法恢复，所以t2.join一直等待

        t1.join();
        t2.join();
    }
}
