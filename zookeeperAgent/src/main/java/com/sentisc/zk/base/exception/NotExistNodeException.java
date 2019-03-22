package com.sentisc.zk.base.exception;

public class NotExistNodeException extends RuntimeException {
    public NotExistNodeException() {
    }

    public NotExistNodeException(String message) {
        super(message);
    }

    public NotExistNodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistNodeException(Throwable cause) {
        super(cause);
    }
}
