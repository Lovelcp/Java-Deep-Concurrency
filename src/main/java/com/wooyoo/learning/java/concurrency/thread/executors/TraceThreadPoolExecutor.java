package com.wooyoo.learning.java.concurrency.thread.executors;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TraceThreadPoolExecutor extends ThreadPoolExecutor {
    public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public void execute(Runnable command) {
        super.execute(wrap(command, clientTrace(), Thread.currentThread()
                                                         .getName()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(wrap(task, clientTrace(), Thread.currentThread()
                                                            .getName()));
    }

    private Exception clientTrace() {
        return new Exception("Client stack trace");
    }

    private Runnable wrap(final Runnable task, final Exception clientStack, String clientThreadName) {
        return () -> {
            try {
                task.run();
            }
            catch (Exception e) {
                System.out.println("client thread is:" + clientThreadName);
                clientStack.printStackTrace();
                throw e;
            }
        };
    }

    public static void main(String[] args) {
        ThreadPoolExecutor pool = new TraceThreadPoolExecutor(0, Integer.MAX_VALUE, 0L, TimeUnit.SECONDS, new SynchronousQueue<>());

        for (int i = 0; i < 5; i++) {
            pool.execute(new DivTask(100, i));
        }
    }
}
