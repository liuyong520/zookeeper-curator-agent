package com.sentisc.zk.base.exception;

public class UpdateNodeException extends RuntimeException {
    public UpdateNodeException() {
    }

    public UpdateNodeException(String message) {
        super(message);
    }

    public UpdateNodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateNodeException(Throwable cause) {
        super(cause);
    }
}
