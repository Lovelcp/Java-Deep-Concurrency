package com.wooyoo.learning.java.concurrency.thread;

public class YieldTest {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 20; i++) {
                System.out.println("T1:" + i);
                // 当i为10时，该线程就会把CPU时间让掉，让其他或者自己的线程执行（也就是谁先抢到谁执行）
                if (i == 10) {
                    Thread.yield();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 1; i <= 20; i++) {
                System.out.println("T2:" + i);
                // 当i为10时，该线程就会把CPU时间让掉，让其他或者自己的线程执行（也就是谁先抢到谁执行）
                if (i == 10) {
                    Thread.yield();
                }
            }
        });

        t1.start();
        t2.start();
    }
}
