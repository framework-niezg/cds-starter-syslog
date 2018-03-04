package com.zjcds.common.syslog.util;

import com.zjcds.common.syslog.domain.LogEvent;
import com.zjcds.common.syslog.domain.LogGroup;
import com.zjcds.common.syslog.exception.LogEventNotFoundException;
import com.zjcds.common.syslog.exception.LogGroupNotFoundException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.*;

/**
 * created date：2018-03-01
 * @author niezhegang
 */
@Getter
public class LogRegisterUtils {

    private static Map<String,LogGroup> logGroups = new TreeMap();

    private static Map<String,LogEvent> logEvents = new TreeMap();

    /**
     * 根据日志组名获取日志组对象
     * @param logGroupName
     * @return
     * @throws LogGroupNotFoundException
     */
    public static LogGroup getLogGroup(String logGroupName) throws LogGroupNotFoundException {
        try {
            Assert.hasText(logGroupName,"传入的日志组名称参数不能为空！");
            LogGroup logGroup = logGroups.get(logGroupName);
            Assert.notNull(logGroup,"查询的日志组为空！");
            return logGroup;
        } catch (Exception e) {
            throw new LogGroupNotFoundException("未找到"+logGroupName+",可能该日志组未注册！",e);
        }
    }

    /**
     * 获取所有的日志组
     * @return
     */
    public static List<LogGroup> getAllLogGroup(){
        List<LogGroup> logGroups = new ArrayList<>();
        for(LogGroup logGroup : LogRegisterUtils.logGroups.values()){
            logGroups.add(logGroup);
        }
        return logGroups;
    }

    /**
     * 根据日志组名称和日志事件名称获取日志事件对象
     * @param logGroupName
     * @param logEventName
     * @return
     * @throws LogEventNotFoundException
     */
    public static LogEvent getLogEvent(String logGroupName,String logEventName) throws LogEventNotFoundException {
        try {
            Assert.hasText(logGroupName,"传入的日志组名称参数不能为空！");
            Assert.hasText(logEventName,"传入的日志事件名称参数不能为空！");
            LogEvent logEvent = logEvents.get(generateLogEventKey(logGroupName,logEventName));
            Assert.notNull(logEvent,"查询的日志事件为空！");
            return logEvent;
        } catch (Exception e) {
            throw new LogEventNotFoundException("未找到"+logEventName+",可能该日志事件未注册！",e);
        }
    }

    public static LogEvent getLogEvent(String logEventKey) {
        String[] strings = parseLogEventKey(logEventKey);
        return getLogEvent(strings[0],strings[1]);
    }

    public static String getLogEventKey(LogEvent logEvent){
        return generateLogEventKey(logEvent.getLogGroup().getName(),logEvent.getName());
    }


    protected static String generateLogEventKey(String logGroupName,String logEventName){
        return logGroupName+"~"+logEventName;
    }

    protected static String[] parseLogEventKey(String logEventKey) {
        Assert.hasText(logEventKey,"日志事件key不能为空！");
        String[] strings = StringUtils.split(logEventKey,'~');
        Assert.isTrue(strings.length == 2,"logEventKey格式不正确！");
        return strings;
    }

    /**
     * 注册新的日志组
     * @param logGroup
     */
    public static void registerLogGroup(LogGroup logGroup) {
        logGroups.put(logGroup.getName(),logGroup);
    }

    /**
     * 注册新的日志事件
     * @param logEvent
     */
    public static void registerLogEvent(LogEvent logEvent) {
        logGroups.get(logEvent.getLogGroup().getName()).addLogEvent(logEvent);
        logEvents.put(generateLogEventKey(logEvent.getLogGroup().getName(),logEvent.getName()),logEvent);
    }
}
