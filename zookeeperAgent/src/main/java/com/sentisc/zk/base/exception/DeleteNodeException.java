package com.sentisc.zk.base.exception;

public class DeleteNodeException extends RuntimeException {
    public DeleteNodeException() {
    }

    public DeleteNodeException(String message) {
        super(message);
    }

    public DeleteNodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteNodeException(Throwable cause) {
        super(cause);
    }
}
