<?php
    require_once 'Service/indexService.php';
    
    
    
    function connDatabase(){
        connDatabase_service();
    }
    function getPlanItem(){
        getPlanItem_service();
    }
    function currDayTime(){
        currDayTime_service();
    }
    function createPlan(){
        $params = $_GET;
        if( sizeof($params) > 0 ){
            $planName = $params['planName'];
            $budgetDate = $params['budgetDate'];
            if( !is_numeric($budgetDate) ){
                throw new Exception('参数错误!');
                return ;
            }
            createPlan_service($planName,$budgetDate);
        }
        
    }
    
?>