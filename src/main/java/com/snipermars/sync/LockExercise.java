package com.snipermars.sync;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public interface LockExercise {
    void lock();                // 获取锁（不可中断）
    void lockInterruptibly() throws InterruptedException;  // 可中断获取锁
    boolean tryLock();          // 尝试获取锁（立即返回）
    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;  // 超时获取锁
    void unlock();              // 释放锁
    Condition newCondition();   // 创建条件变量
}
