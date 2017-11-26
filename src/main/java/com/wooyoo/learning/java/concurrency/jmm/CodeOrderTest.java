package com.wooyoo.learning.java.concurrency.jmm;

import java.util.concurrent.CountDownLatch;

public class CodeOrderTest implements Runnable {
    private static int a = 0;
//    private static boolean flag = false;
    private static volatile boolean flag = false;

    private static CountDownLatch startLatch;
    private static CountDownLatch stopLatch;

    private boolean isWriter = false;

    public CodeOrderTest(boolean isWriter) {
        this.isWriter = isWriter;
    }

    public void writer() {
        a = 1;
        flag = true;
    }

    public void reader() {
        if (flag) {
            int i = a + 1;
            if (i == 1) {
                // 只有当发生指令重排的时候，这里的代码才会执行
                System.out.println("i:" + i);
            }
        }
    }

    public void run() {
        try {
            startLatch.await();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (isWriter) {
            writer();
        }
        else {
            reader();
        }
        stopLatch.countDown();
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000000; i++) {
            startLatch = new CountDownLatch(1);
            stopLatch = new CountDownLatch(2);

            Thread w = new Thread(new CodeOrderTest(true));
            w.start();
            Thread r = new Thread(new CodeOrderTest(false));
            r.start();

            // w和r同时开始
            startLatch.countDown();

            // 等待w和r同时结束
            stopLatch.await();

            // 重置变量
            flag = false;
            a = 0;
        }
    }
}
