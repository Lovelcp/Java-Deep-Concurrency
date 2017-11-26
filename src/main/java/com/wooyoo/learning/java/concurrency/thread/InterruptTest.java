package com.wooyoo.learning.java.concurrency.thread;

public class InterruptTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread() {
            //            @Override
            //            public void run() {
            //                while (true) {
            //                    Thread.yield();
            //                }
            //            }

            @Override
            public void run() {
                while (true) {
                    if (Thread.currentThread()
                              .isInterrupted()) {
                        System.out.println("Interrupted!");
                        break;
                    }

                    try {
                        Thread.sleep(2000);
                    }
                    catch (InterruptedException e) {
                        System.out.println("Interrupted when sleep");
                        Thread.currentThread()
                              .interrupt(); // 这个catch会重置interrupt标记位，由于我们需要退出这个线程，所以需要重新interrupt
                    }
                }
            }
        };

        t1.start();
        Thread.sleep(2000);
        t1.interrupt(); // interrupt只是设置了标记位，并不会真的停止线程
    }
}
