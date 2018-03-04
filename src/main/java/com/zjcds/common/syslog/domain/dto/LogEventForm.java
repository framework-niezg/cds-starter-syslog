package com.zjcds.common.syslog.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * created date：2018-03-04
 * @author niezhegang
 */
@Getter
@Setter
@ApiModel(value = "logEvent",description = "日志事件")
public class LogEventForm {
    /**事件名称*/
    @ApiModelProperty(value = "日志事件名称",required = true,example = "userAdd")
    private String name;
    /**显示名称*/
    @ApiModelProperty(value = "日志事件显示名称",readOnly = true,required = true,example = "添加用户")
    private String displayName;
}
