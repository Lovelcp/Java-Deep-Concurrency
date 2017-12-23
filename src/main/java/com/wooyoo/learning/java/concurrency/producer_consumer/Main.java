package com.wooyoo.learning.java.concurrency.producer_consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // 建立缓冲区
        BlockingQueue<PCData> queue = new LinkedBlockingQueue<>(10);
        Producer producer1 = new Producer(queue);
        Producer producer2 = new Producer(queue);
        Producer producer3 = new Producer(queue);
        Consumer consumer1 = new Consumer(queue);
        Consumer consumer2 = new Consumer(queue);
        Consumer consumer3 = new Consumer(queue);

        ExecutorService es = Executors.newCachedThreadPool();

        es.submit(producer1);
        es.submit(producer2);
        es.submit(producer3);
        es.submit(consumer1);
        es.submit(consumer2);
        es.submit(consumer3);

        Thread.sleep(10 * 1000);

        // 停止生产者
        producer1.stop();
        producer2.stop();
        producer3.stop();

        Thread.sleep(3000);
        es.shutdown();
    }
}
