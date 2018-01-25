package com.wooyoo.learning.java.concurrency.thread.executors;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolAddWorker {
    public static void main(String[] args) {
        LinkedBlockingQueue<Runnable> queue =
                new LinkedBlockingQueue<Runnable>(5);
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, queue);
        for (int i = 0; i < 16; i++) {
            threadPool.execute(
                    new Thread(() -> {
                        try {
                            Thread.sleep(300);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }, "Thread".concat(i + "")));
            System.out.println("线程池中活跃的线程数： " + threadPool.getPoolSize());
            if (queue.size() > 0) {
                System.out.println("----------------队列中阻塞的线程数" + queue.size());
            }
        }
        threadPool.shutdown();
    }
}
