package com.entity;

import java.sql.Timestamp;

public class PlanItem {
    private Integer plan_id;
    private String plan_name;
    private String plan_info;
    private Timestamp create_date;
    private Integer plan_state;

    public Integer getPlan_state() {
        return plan_state;
    }

    public void setPlan_state(Integer plan_state) {
        this.plan_state = plan_state;
    }

    public Timestamp getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Timestamp create_date) {
        this.create_date = create_date;
    }

    public Integer getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(Integer plan_id) {
        this.plan_id = plan_id;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getPlan_info() {
        return plan_info;
    }

    public void setPlan_info(String plan_info) {
        this.plan_info = plan_info;
    }
}
