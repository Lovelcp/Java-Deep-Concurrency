package com.wooyoo.learning.java.concurrency.cas.vector;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class LockFreeVector<E> {
    private static final int N_BUCKET = 30;
    private static final int FIRST_BUCKET_SIZE = 8;

    private final AtomicReferenceArray<AtomicReferenceArray<E>> buckets;
    private final AtomicReference<Descriptor<E>> descriptor;
    private final int zeroNumFirst = 28;

    static class Descriptor<E> {
        public int size;
        volatile WriteDescriptor<E> writeOp;

        public Descriptor(int size, WriteDescriptor<E> writeOp) {
            this.size = size;
            this.writeOp = writeOp;
        }

        public void completeWrite() {
            WriteDescriptor<E> tempOp = writeOp;
            if (tempOp != null) {
                tempOp.doIt();
                writeOp = null; // this is safe since all write to writeOp use null as r_value
            }
        }
    }

    static class WriteDescriptor<E> {
        public E oldV;
        public E newV;
        public AtomicReferenceArray<E> addr;
        public int addrIndex;

        public WriteDescriptor(AtomicReferenceArray<E> addr, int addrIndex, E oldV, E newV) {
            this.addr = addr;
            this.addrIndex = addrIndex;
            this.oldV = oldV;
            this.newV = newV;
        }

        public void doIt() {
            addr.compareAndSet(addrIndex, oldV, newV);
        }
    }

    public LockFreeVector() {
        buckets = new AtomicReferenceArray<>(N_BUCKET);
        buckets.set(0, new AtomicReferenceArray<>(FIRST_BUCKET_SIZE));
        descriptor = new AtomicReference<>(new Descriptor<>(0, null));
    }

    public void pushBack(E e) {
        Descriptor<E> desc;
        Descriptor<E> newd;

        do {
            desc = descriptor.get();
            desc.completeWrite();

            int pos = desc.size + FIRST_BUCKET_SIZE;
            int zeroNumPos = Integer.numberOfLeadingZeros(pos);
            int bucketInd = zeroNumFirst - zeroNumPos;
            if (buckets.get(bucketInd) == null) {
                int newLen = 2 * buckets.get(bucketInd - 1).length(); // TODO 移位
                System.out.println("New length is:" + newLen);
                buckets.compareAndSet(bucketInd, null, new AtomicReferenceArray<>(newLen));
            }
            int idx = (0x80000000 >>> zeroNumPos) ^ pos;
            newd = new Descriptor<>(desc.size + 1, new WriteDescriptor<>(buckets.get(bucketInd), idx, null, e));
        } while (!descriptor.compareAndSet(desc, newd));
        descriptor.get().completeWrite();
    }

    public E get(int index) {
        int pos = index + FIRST_BUCKET_SIZE;
        int zeroNumPos = Integer.numberOfLeadingZeros(pos);
        int bucketInd = zeroNumFirst - zeroNumPos;
        int idx = (0x8000000 >>> zeroNumPos) ^ pos;
        return buckets.get(bucketInd).get(idx);
    }
}
