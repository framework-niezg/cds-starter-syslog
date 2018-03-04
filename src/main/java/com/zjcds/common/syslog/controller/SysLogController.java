package com.zjcds.common.syslog.controller;

import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.jpa.PageResult;
import com.zjcds.common.jpa.utils.PageUtils;
import com.zjcds.common.jsonview.utils.ResponseResult;
import com.zjcds.common.syslog.domain.LogEvent;
import com.zjcds.common.syslog.domain.dto.SysLogForm;
import com.zjcds.common.syslog.domain.entity.SysLog;
import com.zjcds.common.syslog.exception.LogEventNotFoundException;
import com.zjcds.common.syslog.service.SysLogService;
import com.zjcds.common.syslog.util.LogRegisterUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * created date：2018-03-03
 * @author niezhegang
 */
@Controller
@RequestMapping("/syslogs")
@Api(description = "系统日志管理控制器")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    @ResponseBody
    @ApiOperation(value ="批量查询系统日志操作，支持分页查询",produces = "application/json;charset=utf-8")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageIndex" ,value = "分页页码" ,defaultValue = "1",dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "limit" ,value = "返回行数",defaultValue = "2147483647",dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "queryString" ,value = "查询条件",defaultValue = "field~Eq~1234",dataType = "String",paramType = "query",allowMultiple = true),
            @ApiImplicitParam(name = "orderBy" ,value = "排序",defaultValue = "field1Desc",dataType = "String",paramType = "query",allowMultiple = true)})
    public ResponseResult<PageResult<SysLogForm>> querySysLogs(Paging paging,
                                                               @RequestParam(required = false,name = "queryString") List<String> queryString,
                                                               @RequestParam(required = false,name = "orderBy") List<String> orderBys) {
        if(orderBys == null) {
            orderBys = new ArrayList<>();
            //默认按操作时间倒序排列
            orderBys.add("createTimeDesc");
        }
        PageResult<SysLog> pageResult = sysLogService.querySysLog(paging,queryString,orderBys);
        PageResult<SysLogForm> pageResultForm = PageUtils.copyPageResult(pageResult, SysLogForm.class);
        fillDisplayName(pageResultForm);
        return new ResponseResult(pageResultForm);
    }

    /**
     * 为返回的系统日志记录填充显示名称
     * @param pageResultForm
     */
    private void fillDisplayName(PageResult<SysLogForm> pageResultForm){
        List<SysLogForm> sysLogForms = pageResultForm.getContent();
        if(CollectionUtils.isNotEmpty(sysLogForms)){
            LogEvent logEvent;
            for (SysLogForm sysLogForm : sysLogForms){
                try {
                    logEvent = LogRegisterUtils.getLogEvent(sysLogForm.getLogGroup(),sysLogForm.getLogEvent());
                    sysLogForm.setLogGroupDisplayName(logEvent.getLogGroup().getDisplayName());
                    sysLogForm.setLogEventDisplayName(logEvent.getDisplayName());
                }
                catch (LogEventNotFoundException e) {
                    logger.warn("未找到注册的日志事件{}-{}",sysLogForm.getLogGroup(),sysLogForm.getLogEvent());
                }
            }
        }
    }

}
