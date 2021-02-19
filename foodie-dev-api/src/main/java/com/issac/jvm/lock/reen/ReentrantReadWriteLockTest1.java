package com.issac.jvm.lock.reen;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: ywy
 * @date: 2021-02-19
 * @desc:
 */
public class ReentrantReadWriteLockTest1 {

    private Object data;

    /**
     * 缓存是否有效
     */
    private volatile boolean cacheValid;

    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public void processCacheData() {
        rwl.readLock().lock();
        // 如果缓存无效，更新cache；否则直接使用data
        if (!cacheValid) {
            // 获取写锁之前必须释放读锁
            rwl.readLock().unlock();
            rwl.writeLock().lock();
            if(!cacheValid) {
                // 更新数据
                data = new Object();
                cacheValid = true;
            }
            // 锁降级，在释放写锁之前获取锁
            rwl.readLock().lock();
            rwl.writeLock().unlock();
        }
        // 使用缓存

        rwl.readLock().unlock();
    }

}
