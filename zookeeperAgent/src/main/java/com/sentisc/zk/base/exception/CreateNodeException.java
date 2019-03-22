package com.sentisc.zk.base.exception;

public class CreateNodeException extends RuntimeException{
    public CreateNodeException() {
    }

    public CreateNodeException(String message) {
        super(message);
    }

    public CreateNodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateNodeException(Throwable cause) {
        super(cause);
    }
}
