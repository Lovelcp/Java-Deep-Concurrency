package com.wooyoo.learning.java.concurrency.disruptor;

public class PCData {
    private long value;

    public void set(long value) {
        this.value = value;
    }

    public long get() {
        return value;
    }
}
