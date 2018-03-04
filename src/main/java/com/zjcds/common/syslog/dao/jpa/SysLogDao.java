package com.zjcds.common.syslog.dao.jpa;

import com.zjcds.common.jpa.CustomRepostory;
import com.zjcds.common.syslog.domain.entity.SysLog;
import org.springframework.stereotype.Repository;

/**
 * created date：2018-03-01
 * @author niezhegang
 */
@Repository
public interface SysLogDao extends CustomRepostory<SysLog,Long> {

}
