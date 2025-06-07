package com.snipermars.sync;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.StampedLock;

public class DistributedLockCache {
    private final ConcurrentHashMap<String, Boolean> lockCache = new ConcurrentHashMap<>();
    private final StampedLock lock = new StampedLock();

    // 检查锁状态（乐观读）
    public boolean isLocked(String resourceId) {
        long stamp = lock.tryOptimisticRead();
        Boolean isLocked = lockCache.get(resourceId);
        
        // 验证读期间是否有写操作
        if (!lock.validate(stamp)) {
            // 有写操作，升级为悲观读锁
            stamp = lock.readLock();
            try {
                isLocked = lockCache.get(resourceId);
            } finally {
                lock.unlockRead(stamp);
            }
        }
        return isLocked != null && isLocked;
    }

    // 更新锁状态（写锁）
    public void updateLockStatus(String resourceId, boolean locked) {
        long stamp = lock.writeLock();
        try {
            lockCache.put(resourceId, locked);
        } finally {
            lock.unlockWrite(stamp);
        }
    }
}