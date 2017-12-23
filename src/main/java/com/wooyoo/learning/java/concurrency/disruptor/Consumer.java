package com.wooyoo.learning.java.concurrency.disruptor;

import com.lmax.disruptor.WorkHandler;

public class Consumer implements WorkHandler<PCData> {
    @Override
    public void onEvent(PCData pcData) throws Exception {
        System.out.println(Thread.currentThread().getId() + ":Event: --" + pcData.get() * pcData.get() + "--");
    }
}
