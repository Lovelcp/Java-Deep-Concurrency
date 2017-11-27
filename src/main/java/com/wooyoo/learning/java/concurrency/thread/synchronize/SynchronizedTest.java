package com.wooyoo.learning.java.concurrency.thread.synchronize;

public class SynchronizedTest {

    public static void main(String[] args) {
        final SyncObj obj = new SyncObj();
        Thread t1 = new Thread(obj::a);
        Thread t2 = new Thread(obj::b);
        Thread t3 = new Thread(obj::c);
        Thread t4 = new Thread(obj::d);
        Thread t5 = new Thread(SyncObj::e);
        Thread t6 = new Thread(SyncObj::f);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
    }
}
