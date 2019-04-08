CREATE TABLE date_item (date_id int NOT NULL AUTO_INCREMENT, plan_id int NOT NULL, begin_date datetime NOT NULL, end_date datetime, PRIMARY KEY (date_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务时间段';

CREATE TABLE plan_group (group_id int NOT NULL AUTO_INCREMENT, group_name varchar(45) COMMENT '组名称', PRIMARY KEY (group_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;


