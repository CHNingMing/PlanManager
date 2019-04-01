DROP TABLE date_item;
CREATE TABLE date_item (date_id int NOT NULL AUTO_INCREMENT, plan_id int NOT NULL, begin_date datetime NOT NULL, end_date datetime, PRIMARY KEY (date_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务时间段';
DROP TABLE plan_item;
CREATE TABLE plan_item (plan_id int NOT NULL AUTO_INCREMENT, plan_name mediumtext NOT NULL, budgetDate int, plan_state int COMMENT '0 未开始	1 进行中 2 暂停 3 失败 4 成功', del_flag tinyint(1), create_date datetime, plan_info varchar(255) COMMENT '任务详细信息', plan_leve tinyint NOT NULL, PRIMARY KEY (plan_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务列表';
