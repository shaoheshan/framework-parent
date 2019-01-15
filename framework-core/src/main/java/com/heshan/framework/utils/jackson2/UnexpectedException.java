package com.heshan.framework.utils.jackson2;

/**
 * UnexpectedException
 *
 */
public class UnexpectedException extends RuntimeException {

    private static final long serialVersionUID = -7575264700466495506L;

    /**
     * 构造方法
     */
    public UnexpectedException() {
    }

    /**
     * 构造方法
     *
     * @param message
     */
    public UnexpectedException(String message) {
        super(message);
    }

    /**
     * 构造方法
     *
     * @param message
     * @param cause
     */
    public UnexpectedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造方法
     *
     * @param cause
     */
    public UnexpectedException(Throwable cause) {
        super(cause);
    }

}