package com.zjcds.common.syslog.domain;

import com.zjcds.common.base.domain.BaseBean;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * created date：2018-03-01
 * @author niezhegang
 */
@Getter
public class LogGroup extends BaseBean{
    /**日志组名称*/
    private String name;
    /**日志组显示名*/
    private String displayName;

    private List<LogEvent> logEvents = new ArrayList<>();

    public void addLogEvent(LogEvent logEvent) {
        this.logEvents.add(logEvent);
    }

    private LogGroup(Builder builder) {
        name = builder.name;
        Assert.hasText(name,"日志组名称不能为空！");
        displayName = builder.displayName;
        if(StringUtils.isBlank(displayName))
            displayName = name;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private String displayName;

        private Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder displayName(String val) {
            displayName = val;
            return this;
        }

        public LogGroup build() {
            return new LogGroup(this);
        }
    }
}
