<?php
    require_once 'data/indexData.php';
    //连接数据库
    function connDatabase_service(){
        connDatabase_data();
    }
    function getPlanItem_service( $planType,$planState,$planName ){
        global  $conn;
        $execSearchSql = "
        select
        plan_id,
        plan_name,
        plan_state,
        budgetDate
         FROM
                ym970u.plan_item
        where del_flag = 0
        ";
        if( $planType != null || $planState != null || $planName != null ){
            if( $planType == "curr_day" ){
                $execSearchSql = $execSearchSql." and date_format(create_date,'%y-%m-%d') = date_format(now(),'%y-%m-%d')";
            }
            if( $planState != -1 ){
                $execSearchSql = $execSearchSql." and plan_state = {$planState}";
            }
            if( sizeof($planName) > 0 ){
                $execSearchSql = $execSearchSql." and plan_name like  '%{$planName}%'";
            }
        }
        $result = mysqli_query($conn, $execSearchSql);
        return $result;
    }
    
    //获取任务列表
    function getPlanItemHtml_service(){
        $result = getPlanItem_service();
        if( !$result ){
            echo "<tr><td colspan='4' class='notdate'>没有相关数据!</td></tr>";
            return;
        }
        if( mysqli_num_rows($result) > 0 ){
            while(  $row = mysqli_fetch_assoc($result) ){
                echo "<tr>";
                echo "<td>{$row['plan_name']}</td>";
                echo "<td>{$row['budgetDate']}</td>";
                echo "<td>";
                //拼接状态列，不同状态不同class,不同planid
                $plan_id = $row['plan_id'];
                $planStateStr = "<span planID='".$plan_id ."'";
                switch ( $row['plan_state'] ){
                    case 0:
                        $planStateStr = $planStateStr." class='am-badge am-radius' title='点击开始任务' ><a class='plan_state' onclick='updatePlanState({$plan_id},1)' href='#'>未开始</a></span>";
                        break;
                    case 1:
                        $planStateStr = $planStateStr." title='点击关闭任务' class='am-badge am-badge-secondary am-radius'><a class='plan_state'  href='#'>进行中</a></span>";
                        break;
                    case 2:
                        $planStateStr = $planStateStr." class='am-badge am-badge-success am-radius'>成功</span>";
                        break;
                }
                echo $planStateStr;
                echo "</td>";
                echo "<td><a href='#'>编辑</a>";
                echo "&nbsp; | &nbsp;";
                echo "<a href='#'>删除</a></td>";
                echo "</tr>";
            }
        }
    }
    //今天奋斗时间
    function currDayTime_service(){
        global  $conn;
        
        $result = mysqli_query($conn, "SELECT left(sum(timediff(end_date,begin_date)),5) currDayTime FROM date_item where date_format(begin_date,'%y-%m-%d') = date_format(now(),'%y-%m-%d');");
        if( mysqli_num_rows($result) > 0 ){
            $rows =  mysqli_fetch_assoc($result);
            echo $rows['currDayTime'];
            return;
        }
        echo '未投入时间';
    }

    /***
     * 创建任务
     * @param unknown $planName
     * @param unknown $budgetDate
     */
    function createPlan_service( $planName,$budgetDate ){
        global $conn;
        $crePlanSql = "insert into plan_item(plan_name,budgetDate,plan_state,del_flag,create_date) values('{$planName}','{$budgetDate}',0,0,now())";
        return mysqli_query($conn, $crePlanSql);
    }

    /***
     * 修改任务状态
     * @param $planId
     * @param $planState　
     * @return bool|mysqli_result|void　
     */
    function updateState( $planId,$planState ){
        global $conn;
        $updatePlanState = "update plan_item set plan_state = '{$planState}' where plan_id =".$planId;
        $insertPlanTime = "";
        if( $planState == 1 ){
            //开始任务
            $insertPlanTime = "insert into date_item(plan_id,begin_date) values({$planId},now())";
        }else{
            //结束任务
            $insertPlanTime = "update date_item set end_date = now() where plan_id = {$planId}";
        }
        //设置状态
        mysqli_query($conn,$updatePlanState);
        //插入时间段
        return mysqli_query($conn, $insertPlanTime);
    }
    
    function del_Plan_service( $planid ){
        global $conn;
        $delSql = "update plan_item set del_flag = 1 where plan_id = {$planid}";
        $result = mysqli_query($conn,$delSql);
        if( $result ){
            return true;
        }
        return false;
    }
    
?>