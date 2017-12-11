package com.wooyoo.learning.java.concurrency.thread.executors;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadTraceTest {
    public static void main(String[] args) {
        ThreadPoolExecutor pools = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0L, TimeUnit.SECONDS, new SynchronousQueue<>());
        for (int i = 0; i < 5; i++) {
//            pools.submit(new DivTask(100, i));
            pools.execute(new DivTask(100, i));
        }
    }
}
