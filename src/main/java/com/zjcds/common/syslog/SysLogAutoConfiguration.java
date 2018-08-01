package com.zjcds.common.syslog;

import com.zjcds.common.syslog.annotation.SysLog;
import com.zjcds.common.syslog.annotation.TemplateContext;
import com.zjcds.common.syslog.annotation.TemplateVariable;
import com.zjcds.common.syslog.domain.LogEvent;
import com.zjcds.common.syslog.domain.SysLogApplicationEvent;
import com.zjcds.common.syslog.service.SpringEventPublishService;
import com.zjcds.common.syslog.util.LogRegisterUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * created date：2018-03-01
 *
 * @author niezhegang
 */
@Configuration
@ComponentScan
public class SysLogAutoConfiguration {

    @Bean
    public SysLogAspect sysLogAspect(SpringEventPublishService springEventPublishService) {
        return new SysLogAspect(springEventPublishService);
    }

    @Aspect
    public static class SysLogAspect implements ApplicationContextAware {

        private SpringEventPublishService springEventPublishService;

        private ApplicationContext applicationContext;

        public SysLogAspect(SpringEventPublishService springEventPublishService) {
            this.springEventPublishService = springEventPublishService;
        }

        @Around("execution(* com.zjcds..*.*(..)) && @annotation(com.zjcds.common.syslog.annotation.SysLog)")
        public void aroundSysLog(ProceedingJoinPoint joinPoint) {
            try {
                Object[] args = joinPoint.getArgs();
                Object result = joinPoint.proceed();
                dealSysLog(joinPoint, args, result);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        private void dealSysLog(ProceedingJoinPoint joinPoint, Object[] args, Object result) {
            try {
                StandardEvaluationContext evaluationContext = prepareSpelEvaluationContext(args, result);
                SysLog sysLog = getSysLogAnnotation(joinPoint);
                Class eventClass = sysLog.eventClass();
                Object source = new SpelExpressionParser().parseExpression(sysLog.source()).getValue(evaluationContext);
                LogEvent logEvent = LogRegisterUtils.getLogEvent(sysLog.logGroup(), sysLog.logEvent());
                String operationUser = new SpelExpressionParser().parseExpression(sysLog.operationUser()).getValue(evaluationContext, String.class);
                String templateText = sysLog.templateText();
                if (!StringUtils.hasText(templateText)) {
                    templateText = logEvent.getTemplateText();
                }
                Map<String, Object> templateContext = dealTemplateContext(sysLog, evaluationContext);
                SysLogApplicationEvent event = SysLogApplicationEvent.newBuilder(eventClass)
                        .source(source)
                        .logEvent(logEvent)
                        .occurDate(new Date())
                        .operationUser(operationUser)
                        .evaluateVariables(templateContext)
                        .templateText(templateText)
                        .build();
                springEventPublishService.publishApplicationEvent(event);
            } catch (NoSuchMethodException e) {

            }
        }

        private SysLog getSysLogAnnotation(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
            Signature s = joinPoint.getSignature();
            Object target = joinPoint.getTarget();
            MethodSignature ms = (MethodSignature) s;
            Method method = target.getClass().getMethod(ms.getName(), ms.getParameterTypes());
            return method.getAnnotation(SysLog.class);
        }

        private Map<String, Object> dealTemplateContext(SysLog sysLog, StandardEvaluationContext evaluationContext) {
            ExpressionParser parser = new SpelExpressionParser();
            Map<String, Object> templateContext = new HashMap<>();
            TemplateContext templateContextAnnotation = sysLog.evaluationContext();
            for (TemplateVariable templateVariable : templateContextAnnotation.variables()) {
                Object value = parser.parseExpression(templateVariable.value()).getValue(evaluationContext);
                templateContext.put(templateVariable.name(), value);
            }
            return templateContext;
        }

        private StandardEvaluationContext prepareSpelEvaluationContext(Object[] args, Object result) {
            StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
            evaluationContext.setBeanResolver(new BeanFactoryResolver(applicationContext));
            evaluationContext.setVariable("args", args);
            evaluationContext.setVariable("result", result);
            return evaluationContext;
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
        }
    }

}
