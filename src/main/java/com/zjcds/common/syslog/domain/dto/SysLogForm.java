package com.zjcds.common.syslog.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * created date：2018-03-04
 * @author niezhegang
 */
@Getter
@Setter
@ApiModel(value = "sysLog",description = "系统日志记录")
public class SysLogForm {
    @ApiModelProperty(value = "id",readOnly = true,required = true)
    private Long id;
    @ApiModelProperty(value = "操作用户",readOnly = true,required = true,example = "root")
    private String operationUser;
    @ApiModelProperty(value = "发生时间",readOnly = true,required = true)
    private Date createTime;
    @ApiModelProperty(value = "日志组名称",readOnly = true,required = true,example = "um")
    private String logGroup;
    @ApiModelProperty(value = "日志组显示名称",readOnly = true,required = true,example = "用户管理")
    private String logGroupDisplayName;
    @ApiModelProperty(value = "日志事件名称",readOnly = true,required = true,example = "userAdd")
    private String logEvent;
    @ApiModelProperty(value = "日志事件显示名称",readOnly = true,required = true,example = "添加用户")
    private String logEventDisplayName;
    @ApiModelProperty(value = "日志详情",readOnly = true,required = true,example = "包含详情信息")
    private String detail;

}
