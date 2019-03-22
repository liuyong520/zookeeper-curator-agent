package com.sentisc.zk.base.lock.fate;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class DistributedReadWriteLock implements ReadWriteLock {
    public Lock readLock() {
        return null;
    }

    public Lock writeLock() {
        return null;
    }
}
