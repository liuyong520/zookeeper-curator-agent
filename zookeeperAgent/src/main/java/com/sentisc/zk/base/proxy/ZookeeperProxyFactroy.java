package com.sentisc.zk.base.proxy;

import com.sentisc.zk.base.core.ZookeeperOperations;
import com.sentisc.zk.base.core.fate.ZookeeperOperationsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;

public class ZookeeperProxyFactroy {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperProxyFactroy.class);
    public static ZookeeperOperations getInstance(){
        ZookeeperOperations delegate = null;
        try {
            delegate = new ZookeeperOperationsImpl();
        }catch (Exception e){

        }
        ZookeeperProxy zookeeperProxy = new ZookeeperProxy(delegate);
        return (ZookeeperOperations) Proxy.newProxyInstance(delegate.getClass().getClassLoader(),delegate.getClass().getInterfaces(),zookeeperProxy);
    }
}
