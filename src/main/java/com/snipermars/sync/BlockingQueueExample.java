package com.snipermars.sync;

import java.util.concurrent.*;

public class BlockingQueueExample {
    private final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    public void producer() throws InterruptedException {
        int value = 0;
        while (true) {
            queue.put(value);
            System.out.println("Produced: " + value);
            value++;
            Thread.sleep(100);
        }
    }

    public void consumer() throws InterruptedException {
        while (true) {
            Integer value = queue.take();
            System.out.println("Consumed: " + value);
            Thread.sleep(300);
        }
    }

    public static void main(String[] args) {
        BlockingQueueExample example = new BlockingQueueExample();
        
        new Thread(() -> {
            try {
                example.producer();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
        
        new Thread(() -> {
            try {
                example.consumer();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}