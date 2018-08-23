package com.lister.itms.exception;

/**
 * Describe :
 * Created by Lister on 2018/6/25 下午7:38.
 * Version : 1.0
 */
public class BizException extends RuntimeException {
    
    private String msg;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public BizException(String message) {
        super(message);
        this.msg = message;
    }

    public String getMsg() {
        return msg;
    }
}
