-- 任务项
use ym970u;
-- drop table plan_item;
-- drop table date_item;
create table plan_item(
	plan_id int auto_increment primary key,		-- 任务ID
    plan_name varchar(65534) not null,	-- 任务名称
    budgetData int null,				-- 预算时间(分钟)
    plan_state	int null ,				-- 任务状态		0 未开始	1 进行中 2 成功 3 失败 
    del_flag	bool null 				-- 删除标记
);
-- 时间项
create table date_item(
	date_id int auto_increment primary key ,-- 时间ID
    plan_id int not null,				-- 任务ID
    begin_date datetime not null,		-- 开始时间
    end_date datetime					-- 结束时间
);

-- 执行SQL
insert into
	plan_item(
		plan_name,
        budgetDate,
        plan_state,
        del_flag)
values
	(
		'任务3',
        '50',
        0,
        0
	);
SELECT LAST_INSERT_ID();


insert into date_item(plan_id,begin_date) value(4,now());

update date_item set end_date=now() where plan_id = 4 and date_id = 2




