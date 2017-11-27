package com.wooyoo.learning.java.concurrency.thread.synchronize;

public class SyncObj {

    public synchronized void a() {
        // 同步方法加锁的是this对象
        System.out.println("method a");
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void b() {
        synchronized (this) {
            System.out.println("method b");
        }
    }

    public void c() {
        synchronized ("test") {
            System.out.println("method c");
        }
    }

    public void d() {
        synchronized (SyncObj.class) {
            // 获取到的是class对象上的锁
            System.out.println("method d");
            try {
                Thread.sleep(2000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized void e() {
        // 静态同步代码块，获取到的是class对象上的锁
        // 同一个类里的静态同步方法会互相互斥
        System.out.println("method e");
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void f() {
        // 静态同步代码块，获取到的是class对象上的锁
        System.out.println("method f");
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
