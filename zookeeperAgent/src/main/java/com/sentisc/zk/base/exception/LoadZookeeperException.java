package com.sentisc.zk.base.exception;

public class LoadZookeeperException extends RuntimeException {
    public LoadZookeeperException(String message) {
        super(message);
    }

    public LoadZookeeperException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoadZookeeperException(Throwable cause) {
        super(cause);
    }


}
