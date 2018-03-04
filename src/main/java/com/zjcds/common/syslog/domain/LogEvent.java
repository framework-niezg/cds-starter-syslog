package com.zjcds.common.syslog.domain;

import com.zjcds.common.base.domain.BaseBean;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 * created date：2018-03-01
 *
 * @author niezhegang
 */
@Getter
public class LogEvent extends BaseBean{
    /**事件名称*/
    private String name;
    /**显示名称*/
    private String displayName;
    /**详情模板*/
    private String templateText;
    /**所属日志组*/
    private LogGroup logGroup;

    private LogEvent(Builder builder) {
        name = builder.name;
        Assert.hasText(name,"日志组名称不能为空！");
        displayName = builder.displayName;
        if(StringUtils.isBlank(displayName))
            displayName = name;
        templateText = builder.templateText;
        logGroup = builder.logGroup;
        Assert.notNull(logGroup,"所属日志组不能为空！");
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private String displayName;
        private String templateText;
        private LogGroup logGroup;

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

        public Builder templateText(String val) {
            templateText = val;
            return this;
        }

        public Builder logGroup(LogGroup val) {
            logGroup = val;
            return this;
        }

        public LogEvent build() {
            return new LogEvent(this);
        }
    }
}
