package com.zjcds.common.syslog.domain.entity;

import javax.persistence.*;
import java.util.Date;


/**
 * created date：2018-03-01
 * @author niezhegang
 */
@Entity
@Table(name = "T_SYS_LOG")
public class SysLog {
    private Long id;
    private String operationUser;
    private Date createTime;
    private String logGroup;
    private String logEvent;
    private String detail;

    @Id
    @Column(name = "ID")
    @TableGenerator(name = "idGenerator",table = "t_id_generator",pkColumnName = "id_key",pkColumnValue = "sysLog",valueColumnName = "id_value")
    @GeneratedValue(generator = "idGenerator",strategy = GenerationType.TABLE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "OPERATION_USER")
    public String getOperationUser() {
        return operationUser;
    }

    public void setOperationUser(String operationUser) {
        this.operationUser = operationUser;
    }

    @Basic
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "LOG_GROUP")
    public String getLogGroup() {
        return logGroup;
    }

    public void setLogGroup(String logGroup) {
        this.logGroup = logGroup;
    }

    @Basic
    @Column(name = "LOG_EVENT")
    public String getLogEvent() {
        return logEvent;
    }

    public void setLogEvent(String logEvent) {
        this.logEvent = logEvent;
    }

    @Basic
    @Column(name = "DETAIL")
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


}
