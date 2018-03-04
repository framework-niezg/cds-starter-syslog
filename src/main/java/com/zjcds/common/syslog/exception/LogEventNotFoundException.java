package com.zjcds.common.syslog.exception;

/**
 * created date：2018-03-02
 *
 * @author niezhegang
 */
public class LogEventNotFoundException extends RuntimeException{

    public LogEventNotFoundException() {
    }

    public LogEventNotFoundException(String message) {
        super(message);
    }

    public LogEventNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogEventNotFoundException(Throwable cause) {
        super(cause);
    }

    public LogEventNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
