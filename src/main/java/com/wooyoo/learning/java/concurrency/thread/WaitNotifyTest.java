package com.wooyoo.learning.java.concurrency.thread;

public class WaitNotifyTest {
    final static Object object = new Object();

    public static class T1 extends Thread {
        public void run() {
            synchronized (object) {
                System.out.println(System.currentTimeMillis() + ":T1 start");
                try {
                    System.out.println(System.currentTimeMillis() + ":T1 wait for object");
                    object.wait();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + ":T1 finish");
            }
        }
    }

    public static class T2 extends Thread {
        public void run() {
            synchronized (object) {
                System.out.println(System.currentTimeMillis() + ":T2 start");
                System.out.println(System.currentTimeMillis() + ":T2 notify one thread");
                object.notify();
                System.out.println(System.currentTimeMillis() + ":T2 finish");
                try {
                    Thread.sleep(2000); // T1需要等待T2释放锁，即不仅notify了没用，还需要释放锁
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new T1().start();
        new T2().start();
    }
}
