package com.zjcds.common.syslog.annotation;

import com.zjcds.common.syslog.domain.SysLogApplicationEvent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author luokp on 2018/7/24.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SysLog {

    Class<? extends SysLogApplicationEvent> eventClass() default SysLogApplicationEvent.class;

    String source();

    String logGroup();

    String logEvent();

    String operationUser();

    String templateText() default "";

    TemplateContext evaluationContext() default @TemplateContext();

}
