package com.zjcds.common.syslog.service;

import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.jpa.PageResult;
import com.zjcds.common.syslog.domain.SysLogApplicationEvent;
import com.zjcds.common.syslog.dao.jpa.SysLogDao;
import com.zjcds.common.syslog.domain.entity.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * created date：2018-03-01
 * @author niezhegang
 */
@Service
@Transactional(readOnly = true)
public class SysLogService {
    @Autowired
    private SysLogDao sysLogDao;

    /**
     * 查询系统日志
     * @param paging
     * @param queryString
     * @param orderBys
     * @return
     */
    public PageResult<SysLog> querySysLog(Paging paging, List<String> queryString, List<String> orderBys){
        return sysLogDao.findAll(paging,queryString,orderBys);
    }

    @Transactional
    public SysLog saveSysLogApplicationEvent(SysLogApplicationEvent sysLogApplicationEvent){
        SysLog sysLog = new SysLog();
        sysLog.setLogGroup(sysLogApplicationEvent.getLogEvent().getLogGroup().getName());
        sysLog.setLogEvent(sysLogApplicationEvent.getLogEvent().getName());
        sysLog.setOperationUser(sysLogApplicationEvent.getOperationUser());
        sysLog.setCreateTime(sysLogApplicationEvent.getOccurDate());
        sysLog.setDetail(sysLogApplicationEvent.logDetail());
        return sysLogDao.save(sysLog);
    }

}
