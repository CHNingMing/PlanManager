<?php
    /* JSON接口 */
    //任务对象
    class Plan{
        public $plan_name ;
        public  $budgetDate = 0;
        public  $plan_id = 0;
        public  $plan_state;
    }
    //响应对象
    class ResponePojo{
        public $status = 0;
        public function getJson(){
            return json_encode($this);
        }
        public function put( $key , $value ){
            $this->$key = $value;
        }
    }
    header('Content-Type:text/xml; charset=utf-8');
    require 'data/indexData.php';
    require 'Service/indexService.php';
    connDatabase_data();
    
    onRequest();
    /**
     * 请求入口
     */
    function onRequest(){
        header('Content-Type:text/json; charset=utf-8');
        $funName = $_SERVER['REQUEST_URI'];
        //去掉请求连接，只剩下最后一个url路径，来当执行方法
        $funName_2 = strrchr($funName,"/");
        $p_index = strpos($funName_2, "?");
        //不带参数时处理
        if( !$p_index ){
            $p_index = strlen($funName_2);
        }
        $funName_3 = substr($funName_2, 1,$p_index-1);
        if( !$funName_3 ){
            echo "请输入请求方法！";
            return;
        }
        //判断函数是否存在
        if( !function_exists($funName_3) ){
            //响应对象
            $data = array("state"=>1,"msg"=>"函数不存在");
            exit(json_encode($data)); 
            return;
        }
        //执行指定方法
        //获取方法参数实例名，调整参数顺序
        $param_arr =  getFucntionParameterName($funName_3);
        call_user_func_array($funName_3,$param_arr);
    }
    
    /**
     * 获取任务列表
     * @param unknown $param
     */
    function getPlanList( $planType,$planState,$planName ){
        header('Content-Type:text/json; charset=utf-8');
        $resp = new ResponePojo();
        global $conn;
        $resp = new ResponePojo();
        //传参打开页面情况
        $result=null;
        if( $planType != null || $planState != null ){
            $result =  getPlanItem_service($planType,$planState,$planName);
        }else{
            $result =  getPlanItem_service();
        }
        
        $planList = array();
        if( !$result ){
            $resp->put("status",1);
            echo json_encode($resp);
            return;
        }
        while( $row = mysqli_fetch_assoc($result) ){
            $plan = new Plan();
            $plan->plan_name = $row['plan_name'];
            $plan->plan_id = $row['plan_id'];
            $plan->budgetDate = $row['budgetDate'];
            $plan->plan_state = $row['plan_state'];
            array_push($planList,$plan);
        }
        $resp->put("planList", $planList);
        echo $resp->getJson();
    }
    
    /**
     * 修改状态
     * @param unknown $param
     * @param unknown $name
     * @param unknown $age
     */
    function updateState_api( $plan_id,$plan_state){
        updateState($plan_id,$plan_state);
        $resp_pojo = new ResponePojo();
        echo $resp_pojo->getJson();
    }
    
    
    /***
     * 通过方法名获取实例参数名称
     * @param unknown $func
     * @return NULL[]
     */
    function getFucntionParameterName($func) {
        $ReflectionFunc = new \ReflectionFunction($func);
        $param = array();
        foreach ($ReflectionFunc->getParameters() as $value) {
            $param[] = $_GET[$value->name];
        }
        return $param;
    }
    /**
     * 创建任务
     * @param unknown $planName
     * @param unknown $budgetDate
     */
    function createPlan( $planName,$budgetDate ){
        $result = createPlan_service($planName,$budgetDate);
        $resp = new ResponePojo();
        if( $result ){
            echo $resp->getJson();
            return;
        }
        $resp->put("status", 1);
        echo $resp;
    }
    /**
     * 设置删除标记
     * @param unknown $planId
     */
    function del_plan( $planId ){
        $resp = new ResponePojo();
        if ( del_Plan_service( $planId ) ){
            $resp->put("msg", "删除{$planId}成功！");
            echo $resp->getJson();
            return;
        }
        echo $resp->getJson();
    }

    /***
     * 获取任务时间段
     * @param $planid 任务id
     */
    function planTimeSlot( $planid ){


    }

?>