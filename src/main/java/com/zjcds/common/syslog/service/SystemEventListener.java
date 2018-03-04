package com.zjcds.common.syslog.service;

import com.zjcds.common.syslog.domain.SysLogApplicationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * created date：2018-03-01
 * @author niezhegang
 */
@Service
public class SystemEventListener {
    @Autowired
    private SysLogService sysLogService;

    @EventListener
    @Async
    public void sysLogEvent(SysLogApplicationEvent sysLogApplicationEvent){
        sysLogService.saveSysLogApplicationEvent(sysLogApplicationEvent);
    }

}
