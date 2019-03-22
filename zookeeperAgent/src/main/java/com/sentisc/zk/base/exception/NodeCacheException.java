package com.sentisc.zk.base.exception;

public class NodeCacheException extends RuntimeException {
    public NodeCacheException() {
    }

    public NodeCacheException(String message) {
        super(message);
    }

    public NodeCacheException(String message, Throwable cause) {
        super(message, cause);
    }

    public NodeCacheException(Throwable cause) {
        super(cause);
    }
}
