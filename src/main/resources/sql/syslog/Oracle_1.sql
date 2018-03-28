-- 系统日志表
CREATE TABLE t_sys_log(id INT,operation_user VARCHAR2(30) NOT NULL,create_time DATE NOT NULL,log_group VARCHAR2(40) not null ,log_event VARCHAR2(40) NOT NULL ,detail VARCHAR2(1000) NOT NULL ,PRIMARY KEY (id));
CREATE INDEX index_sys_log_1 ON t_sys_log(create_time,log_group,log_event,operation_user);
CREATE INDEX index_sys_log_2 ON t_sys_log(create_time,operation_user,log_group,log_event);
INSERT INTO t_id_generator(id_key,id_value) VALUES ('sysLog',1);