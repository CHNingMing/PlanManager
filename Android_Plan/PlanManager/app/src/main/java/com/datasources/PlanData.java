package com.datasources;

import com.entity.PlanItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 19-3-31.
 * 任务管理数据库相关操作
 */

public class PlanData {

    /***
     * 获取指定状态任务
     * @param del_flag 删除标记 true:删除  false:未删除
     * @param plan_state
     * @return
     */
    public static List<PlanItem> getPlanItemsByState(boolean del_flag, Date date,int... plan_state){
        String where = "";
        for( int i = 0; i < plan_state.length; i++ ){
            if( i == 0 ){
                where += " plan_state in (";
            }
            where += plan_state[i]+",";
        }
        where = where.substring(0,where.length() - 1) + ")";
        if( del_flag ){
            where = " where del_flag = false and "+where;
        }else{
            where = " where "+where;
        }
        if( date != null ){
            //处理日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date_str =  sdf.format(date);
            where += " and date_format(create_date,'%Y-%m-%d') = '"+date_str+"'";
        }


        List<PlanItem> planItemList = MySqlDS.querySql("select * from ym970u.plan_item"+where, PlanItem.class);
        return planItemList;
    }
    public static List<PlanItem> getPlanItemsByState(boolean del_flag, int... plan_state){
        return getPlanItemsByState(del_flag,null,plan_state);
    }




}
