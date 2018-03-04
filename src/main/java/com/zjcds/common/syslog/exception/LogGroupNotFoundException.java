package com.zjcds.common.syslog.exception;

/**
 * created date：2018-03-02
 *
 * @author niezhegang
 */
public class LogGroupNotFoundException extends RuntimeException{

    public LogGroupNotFoundException() {
    }

    public LogGroupNotFoundException(String message) {
        super(message);
    }

    public LogGroupNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogGroupNotFoundException(Throwable cause) {
        super(cause);
    }

    public LogGroupNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
