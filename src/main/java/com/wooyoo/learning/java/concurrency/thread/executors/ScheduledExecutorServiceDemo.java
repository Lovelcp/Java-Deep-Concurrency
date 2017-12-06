package com.wooyoo.learning.java.concurrency.thread.executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceDemo {
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
        executor.scheduleAtFixedRate(() -> {
            try {
                Thread.sleep(5000); // 如果过了rate，则会立即执行
                System.out.println(System.currentTimeMillis() / 1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 0, 2, TimeUnit.SECONDS);
    }
}
