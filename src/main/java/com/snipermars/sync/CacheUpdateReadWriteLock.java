package com.snipermars.sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CacheUpdateReadWriteLock {
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private String cachedData;

    // 读取缓存（允许多线程并发）
    public String readData() {
        rwLock.readLock().lock();
        try {
            return cachedData;
        } finally {
            rwLock.readLock().unlock();
        }
    }

    // 更新缓存（独占操作）
    public void updateData(String newData) {
        rwLock.writeLock().lock();
        try {
            // 模拟耗时更新
            Thread.sleep(100);
            cachedData = newData;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}