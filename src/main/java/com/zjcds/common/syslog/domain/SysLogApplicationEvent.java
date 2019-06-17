package com.zjcds.common.syslog.domain;

import com.zjcds.common.syslog.util.LogRegisterUtils;
import com.zjcds.common.syslog.util.TemplateUtils;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * created date：2018-03-01
 * @author niezhegang
 */
public class SysLogApplicationEvent extends ApplicationEvent {

    public SysLogApplicationEvent(Object source) {
        super(source);
    }

    private LogEvent logEvent;

    private String operationUser;

    private Date occurDate;

    private Map<String,Object> evaluateContext;

    private String templateText;

    public LogEvent getLogEvent() {
        return logEvent;
    }

    protected void setLogEvent(LogEvent logEvent) {
        this.logEvent = logEvent;
    }

    public String getOperationUser() {
        return operationUser;
    }

    protected void setOperationUser(String operationUser) {
        this.operationUser = operationUser;
    }

    public Date getOccurDate() {
        return occurDate;
    }

    protected void setOccurDate(Date occurDate) {
        this.occurDate = occurDate;
    }

    public Map<String, Object> getEvaluateContext() {
        return evaluateContext;
    }

    protected void setEvaluateContext(Map<String, Object> evaluateContext) {
        this.evaluateContext = evaluateContext;
    }

    public String getTemplateText() {
        return templateText;
    }

    public void setTemplateText(String templateText) {
        this.templateText = templateText;
    }

    public String logDetail() {
        Map<String,Object> evaluateContextForAll = new HashMap<>();
        evaluateContextForAll.putAll(evaluateContext);
        appendExtendContextVariate(evaluateContextForAll);
        return TemplateUtils.evaluateTemplate(templateText,evaluateContextForAll);
    }

    /**
     * 添加额外的评估变量
     * @param evaluateContext
     */
    private void appendExtendContextVariate(Map<String,Object> evaluateContext){
        evaluateContext.put("operationUser",operationUser);
        evaluateContext.put("occurDate",occurDate);
        evaluateContext.put("eventSource",source);
    }
    public static <T extends SysLogApplicationEvent> Builder<T> newBuilder(Class<T> instanceClass){
        return new Builder<>(instanceClass);
    }

    public static class Builder<T extends SysLogApplicationEvent> {
        private Object source;
        private LogEvent logEvent;
        private String operationUser;
        private Date occurDate;
        private Class<T> instanceClass;
        private Map<String,Object> evaluateContext = new HashMap<>();
        private String templateText;

        private Builder(Class<T> instanceClass) {
            this.instanceClass = instanceClass;
        }

        public Builder<T> source(Object val) {
            source = val;
            return this;
        }

        public Builder<T> logEvent(LogEvent val) {
            logEvent = val;
            return this;
        }

        public Builder<T> operationUser(String val) {
            operationUser = val;
            return this;
        }

        public Builder<T> occurDate(Date val) {
            occurDate = val;
            return this;
        }

        public Builder<T> evaluateVariable(String key,Object value){
            Assert.notNull(key,"评估变量的key值不能为空！");
            evaluateContext.put(key,value);
            return this;
        }

        public Builder<T> evaluateVariables(Map<String, Object> variables) {
            evaluateContext.putAll(variables);
            return this;
        }

        public Builder<T> templateText(String val) {
            templateText = val;
            return this;
        }

        public T build() {
            Assert.notNull(source,"事件源对象不能为空！");
            T sysLogApplicationEvent;
            try {
                sysLogApplicationEvent =  instanceClass.getConstructor(Object.class).newInstance(source);
            }
            catch (Exception e){
                throw new IllegalArgumentException(instanceClass.getName()+"实例构件失败！",e);
            }
            Assert.notNull(logEvent,"日志事件不能为空！");
            sysLogApplicationEvent.setLogEvent(logEvent);
            Assert.hasText(operationUser,"操作用户不能为空！");
            sysLogApplicationEvent.setOperationUser(operationUser);
            if(occurDate == null)
                occurDate = new Date();
            sysLogApplicationEvent.setOccurDate(occurDate);
            sysLogApplicationEvent.setEvaluateContext(evaluateContext);
            Assert.hasText(templateText, "模板文本不能为空！");
            sysLogApplicationEvent.setTemplateText(templateText);
            return sysLogApplicationEvent;

        }
    }
}
