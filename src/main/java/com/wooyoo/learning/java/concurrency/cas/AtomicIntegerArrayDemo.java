package com.wooyoo.learning.java.concurrency.cas;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayDemo {
    static AtomicIntegerArray arr = new AtomicIntegerArray(10);

    public static class AddThread implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                arr.getAndIncrement(i % arr.length());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] ts = new Thread[10];
        for (int i = 0; i < 10; i++) {
            ts[i] = new Thread(new AddThread());
        }
        for (int i = 0; i < 10; i++) {
            ts[i].start();
        }
        for (int i = 0; i < 10; i++) {
            ts[i].join();
        }
        System.out.println(arr);
    }
}
