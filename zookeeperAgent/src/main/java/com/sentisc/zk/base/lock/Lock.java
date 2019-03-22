package com.sentisc.zk.base.lock;

import java.util.concurrent.locks.Condition;

public abstract class Lock implements java.util.concurrent.locks.Lock {

    public Condition newCondition() {
        return null;
    }
}
