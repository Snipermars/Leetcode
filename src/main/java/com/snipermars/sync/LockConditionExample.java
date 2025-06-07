package com.snipermars.sync;

import java.util.concurrent.locks.*;
import java.util.stream.IntStream;

public class LockConditionExample {
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    private final int[] buffer = new int[10];
    private int count, putIndex, takeIndex;

    public void put(int value) throws InterruptedException {
        lock.lock();
        try {
            while (count == buffer.length) {
                notFull.await(); 
            }
            buffer[putIndex] = value;
            if (++putIndex == buffer.length) putIndex = 0;
            count++;
            notEmpty.signal(); 
        } finally {
            lock.unlock();
        }
    }

    public int take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await(); 
            }
            int value = buffer[takeIndex];
            if (++takeIndex == buffer.length) takeIndex = 0;
            count--;
            notFull.signal();
            return value;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        LockConditionExample example = new LockConditionExample();
        
        IntStream.range(0, 3).forEach(i -> new Thread(() -> {
            while (true) {
                try {
                    int value = (int)(Math.random() * 100);
                    example.put(value);
                    System.out.println("Producer " + i + " put: " + value);
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start());
        
        IntStream.range(0, 2).forEach(i -> new Thread(() -> {
            while (true) {
                try {
                    int value = example.take();
                    System.out.println("Consumer " + i + " took: " + value);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start());
    }
}
