package com.zjcds.common.syslog.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luokp on 2018/8/22.
 */
public class SysLogEvaluationContextUtils {

    private static ThreadLocal<Map<String, Object>> evaluationContext = new ThreadLocal<>();

    public static void setEvaluationContext(String name, Object value) {
        if (evaluationContext.get() == null) {
            evaluationContext.set(new HashMap<>());
        }
        evaluationContext.get().put(name, value);
    }

    public static Map<String, Object> getEvaluationContext() {
        return evaluationContext.get();
    }

    public static void clearEvaluationContext() {
        evaluationContext.remove();
    }

}
