package com.snipermars.sync;

import java.util.concurrent.*;

public class CompleteTableFutureExample {
    public static void main(String[] args) {
        // 链式异步调用
        CompletableFuture.supplyAsync(() -> {
            System.out.println("Step 1 running in " + Thread.currentThread().getName());
            return "Hello";
        }).thenApplyAsync(result -> {
            System.out.println("Step 2 running in " + Thread.currentThread().getName());
            return result + " World";
        }).thenAcceptAsync(finalResult -> {
            System.out.println("Final result: " + finalResult);
        }).join(); 
        
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "World");
        
        future1.thenCombine(future2, (s1, s2) -> s1 + "," + s2)
               .thenAccept(System.out::println)
               .join();
    }
}