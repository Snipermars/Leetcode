package com.snipermars.sync;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ResourcePool<T> {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition available = lock.newCondition();
    private final T[] resources;
    private final boolean[] used;

    @SuppressWarnings("unchecked")
    public ResourcePool(int size) {
        resources = (T[]) new Object[size];
        used = new boolean[size];
        // 初始化资源（示例略）
    }

    // 获取资源（支持超时）
    public T acquire(long timeout, TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        lock.lockInterruptibly();
        try {
            while (true) {
                // 查找可用资源
                for (int i = 0; i < resources.length; i++) {
                    if (!used[i]) {
                        used[i] = true;
                        return resources[i];
                    }
                }
                // 无可用资源，等待
                if (nanos <= 0) return null;
                nanos = available.awaitNanos(nanos);
            }
        } finally {
            lock.unlock();
        }
    }

    // 释放资源
    public void release(T resource) {
        lock.lock();
        try {
            // 查找资源并标记为可用
            for (int i = 0; i < resources.length; i++) {
                if (resources[i] == resource) {
                    used[i] = false;
                    available.signal(); // 唤醒等待线程
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
    }
    
}
