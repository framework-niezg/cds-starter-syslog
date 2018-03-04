package com.zjcds.common.syslog.controller;

import com.zjcds.common.dozer.BeanPropertyCopyUtils;
import com.zjcds.common.jsonview.annotations.JsonViewException;
import com.zjcds.common.jsonview.utils.ResponseResult;
import com.zjcds.common.syslog.domain.dto.LogGroupForm;
import com.zjcds.common.syslog.util.LogRegisterUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * created date：2018-03-04
 * @author niezhegang
 */
@RestController
@RequestMapping("/logGroups")
@Api(description = "日志组控制器")
public class LogGroupController {

    @GetMapping
    @JsonViewException
    @ApiOperation(value ="返回所有的日志组",produces = "application/json;charset=utf-8")
    public ResponseResult<List<LogGroupForm>> logGroups() {
        return new ResponseResult<>(BeanPropertyCopyUtils.copy(LogRegisterUtils.getAllLogGroup(), LogGroupForm.class));
    }
}
