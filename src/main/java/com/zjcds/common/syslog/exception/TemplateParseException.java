package com.zjcds.common.syslog.exception;

/**
 * created date：2018-03-03
 *
 * @author niezhegang
 */
public class TemplateParseException extends RuntimeException{

    public TemplateParseException() {
    }

    public TemplateParseException(String message) {
        super(message);
    }

    public TemplateParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public TemplateParseException(Throwable cause) {
        super(cause);
    }

    public TemplateParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
