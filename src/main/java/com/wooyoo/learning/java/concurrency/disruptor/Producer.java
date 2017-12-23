package com.wooyoo.learning.java.concurrency.disruptor;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

public class Producer {
    private final RingBuffer<PCData> ringBuffer;

    public Producer(RingBuffer<PCData> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void pushData(ByteBuffer bb) {
        long sequence = ringBuffer.next(); // Grab the next sequence
        try {
            PCData event = ringBuffer.get(sequence); // Get the entry in the Disruptor for the sequence
            event.set(bb.getLong(0)); // Fill the data
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
