package com.zjcds.common.syslog.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * created date：2018-03-04
 * @author niezhegang
 */
@Getter
@Setter
@ApiModel(value = "logGroup",description = "日志组")
public class LogGroupForm {
    /**日志组名称*/
    @ApiModelProperty(value = "日志组名称",readOnly = true,required = true,example = "um")
    private String name;
    /**日志组显示名*/
    @ApiModelProperty(value = "日志组显示名称",readOnly = true,required = true,example = "用户管理")
    private String displayName;
    @ApiModelProperty(value = "日志组包含的日志事件",readOnly = true)
    private List<LogEventForm> logEvents = new ArrayList<>();
}
