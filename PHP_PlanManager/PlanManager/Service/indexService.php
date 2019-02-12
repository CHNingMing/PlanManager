<?php
    require_once 'data/indexData.php';
    function connDatabase_service(){
        connDatabase_data();
    }
    //继承数据库链接对象
    
    
    //获取任务列表
    function getPlanItem_service(){
        global  $conn;
        $execSearchSql = "
        select
        	planItem.plan_id,
            begin_date,
            end_date,
            plan_name,
            plan_state,
            budgetDate
        from
        	date_item dateItem
            right join plan_item planItem on planItem.plan_id = dateItem.plan_id
        where del_flag = 0
        ";
        
        
        //传参打开页面情况
        if( sizeof($_POST) > 0 ){
            
            if( $_POST['planType'] == "curr_day" ){
                $execSearchSql = $execSearchSql." and date_format(begin_date,'%y-%m-%d') = date_format(now(),'%y-%m-%d')";
            }
            if( $_POST['planState'] != -1 ){
                $execSearchSql = $execSearchSql." and plan_state = ".$_POST['planState'];
            }
            
        }
        
        $result = mysqli_query($conn, $execSearchSql);
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
                switch ( $row['plan_state'] ){
                    case 0:
                        echo "未开始";
                        break;
                    case 1:
                        echo "进行中..";
                        break;
                    case 2:
                        echo "成功";
                        break;
                    case 3:
                        echo "<span class='planState_fail'>失败</span>";
                        break;
                }
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
        
        $result = mysqli_query($conn, "SELECT date_id,plan_id,timediff(end_date,begin_date) currDayTime FROM date_item where date_format(begin_date,'%y-%m-%d') = date_format(now(),'%y-%m-%d');");
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
        $crePlanSql = "insert into plan_item(plan_name,budgetDate,plan_state,del_flag) values('{$planName}','{$budgetDate}',0,0); 
                               SELECT LAST_INSERT_ID();";
        
        
    }
    
?>