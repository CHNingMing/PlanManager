package com.entity;

import com.mysqlutil.annotation.DBField;
import com.mysqlutil.annotation.FieldType;
import com.mysqlutil.annotation.GoalTable;
import com.mysqlutil.annotation.PrimaryKey;

import java.sql.Timestamp;

/**
 * 任务时间段
 */
@GoalTable(tablename = "date_item")
public class PlanDateItem {

    @PrimaryKey
    @DBField(not_null = true,field_type = FieldType.Int,field_name = "date_id")
    private Integer dateId;
    @DBField(not_null = true,field_type = FieldType.Int,field_name = "plan_id")
    private Integer planId;
    @DBField(field_type = FieldType.DateTime,not_null = true,field_name = "begin_date")
    private Timestamp BeginDate;
    @DBField(field_type = FieldType.DateTime,not_null = false,field_name = "end_date",date_format = "yyyy-MM-dd hh:mm:ss")
    private Timestamp EndDate;

    public Integer getDateId() {
        return dateId;
    }

    public void setDateId(Integer dateId) {
        this.dateId = dateId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Timestamp getBeginDate() {
        return BeginDate;
    }

    public void setBeginDate(Timestamp beginDate) {
        BeginDate = beginDate;
    }

    public Timestamp getEndDate() {
        return EndDate;
    }

    public void setEndDate(Timestamp endDate) {
        EndDate = endDate;
    }


}
