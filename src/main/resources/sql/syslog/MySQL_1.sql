-- 系统日志表
CREATE TABLE t_sys_log(id INT PRIMARY KEY ,operation_user VARCHAR(30) NOT NULL,create_time DATETIME NOT NULL ,log_group VARCHAR(40) not null,log_event VARCHAR(40) NOT NULL ,detail VARCHAR(1000) NOT NULL )
CREATE INDEX index_sys_log_1 ON t_sys_log(create_time,log_group,log_event,operation_user)
CREATE INDEX index_sys_log_2 ON t_sys_log(create_time,operation_user,log_group,log_event)
INSERT INTO t_id_generator(id_key,id_value) VALUES ('sysLog',1)
