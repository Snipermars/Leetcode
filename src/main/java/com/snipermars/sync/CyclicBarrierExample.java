package com.snipermars.sync;

import java.util.concurrent.*;

public class CyclicBarrierExample {
    public static void main(String[] args) {
        int threadCount = 3;
        CyclicBarrier barrier = new CyclicBarrier(threadCount, () -> 
            System.out.println("All threads reached barrier. Continuing..."));
        
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            new Thread(() -> {
                try {
                    System.out.println("Thread " + threadNum + " phase 1");
                    Thread.sleep(1000);
                    barrier.await();
                    
                    System.out.println("Thread " + threadNum + " phase 2");
                    Thread.sleep(1500);
                    barrier.await();
                    
                    System.out.println("Thread " + threadNum + " completed");
                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
}
