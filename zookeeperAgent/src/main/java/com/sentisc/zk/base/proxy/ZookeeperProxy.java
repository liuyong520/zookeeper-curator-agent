package com.sentisc.zk.base.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ZookeeperProxy implements InvocationHandler {
    public static final Logger logger = LoggerFactory.getLogger(ZookeeperProxy.class);

    private Object delegate;

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(delegate,args);
    }

    public ZookeeperProxy(Object delegate) {
        this.delegate = delegate;
    }
}
