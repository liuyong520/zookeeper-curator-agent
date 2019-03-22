package com.sentisc.zk.base.lock.fate;

import com.sentisc.zk.base.core.ZookeeperOperations;
import com.sentisc.zk.base.lock.Lock;
import com.sentisc.zk.base.proxy.ZookeeperProxyFactroy;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.TimeUnit;

public class DistributedExclusiveLock extends Lock {

    private static final ZookeeperOperations proxy;
    private static final int expireMsecs = 60 * 1000;
    private static final int timeoutMsecs = 10 * 1000;

    static {
        proxy = ZookeeperProxyFactroy.getInstance();
    }


    boolean locked = false;
    private String lockKeyNode;
    private String lockKey;
    public DistributedExclusiveLock(String lockKey) {
        this.lockKey = lockKey;
        this.lockKeyNode = ZKPaths.makePath(lockKey,"");

    }

    public void lock() {

    }

    public void lockInterruptibly() throws InterruptedException {
        if (Thread.interrupted()){
            throw new InterruptedException();
        }
    }

    public boolean tryLock() {
        try {
            return this.tryLock(timeoutMsecs,TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        long timeout = unit.toSeconds(time);
        while (true){
            try {
                proxy.createTemporaryNode(lockKeyNode,lockKey);
                this.locked = true;
            }catch (Exception e){
                this.locked = false;
            }
            if (timeout <= 0) {
                break;
            }

            timeout -= 100;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
        return this.locked;
    }

    public void unlock() {
        if(this.locked){
            try {
                Stat stat = proxy.existNode(lockKeyNode);
                if(stat!=null){
                    proxy.deleteNode(lockKeyNode);
                }
                this.locked = false;
            }catch (Exception e){
                throw new RuntimeException("DistributedExclusiveLock 解锁异常");
            }
        }
    }


}
