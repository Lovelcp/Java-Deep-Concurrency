package com.wooyoo.learning.java.concurrency.thread;

public class JoinTest {
    public volatile static int i = 0;

    public static class AddThread extends Thread {
        @Override
        public void run() {
            for (i = 0; i < 10000000; i++)
                ;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AddThread at = new AddThread();
        at.start();
        at.join(); // 等到at结束之后，main线程才能继续执行
        System.out.println(i);
    }
}
