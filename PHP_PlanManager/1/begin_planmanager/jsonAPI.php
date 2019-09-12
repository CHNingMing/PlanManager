<?php
    /* JSON接口 */

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
    header('Content-Type:text/html; charset=utf-8');
    require 'data/indexData.php';
    require 'Service/indexService.php';
    require 'entity/Plan.php';
    connDatabase_data();
    
    onRequest();
    /**
     * 请求入口
     */
    function onRequest(){
        header('Content-Type:text/json; charset=utf-8');
        header("Access-Control-Allow-Origin: *");   //跨域


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
        $result = call_user_func_array($funName_3,$param_arr);
        if( is_span_domain() ){
            /*
            //jsonp跨域
            header('Content-Type:application/x-javascript;charset=utf-8');
            if( isset($_GET['fun_name']) ){
                echo $_GET['fun_name'].'('.$result.');';
            }else{
                echo 'doResponse('.$result.');';
                echo '<script>window.name = "{$result}"</script>';
            }
            */
            //正规跨域
            echo $result;


        }
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
            $result =  getPlanItem_service( null,null,null );
        }
        
        $planList = array();
        if( !$result ){
            $resp->put("status",1);
            return returnValue($resp);
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
        return returnValue($resp);
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
        return returnValue($resp_pojo);
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
    function createPlan( $planName,$budgetDate,$planInfo,$closing_date ){
        $result = createPlan_service($planName,$budgetDate,$planInfo,$closing_date);
        $resp = new ResponePojo();
        if( $result ){
            return returnValue($resp);
        }
        $resp->put("status", 1);
        return returnValue($resp);
    }
    /**
     * 设置删除标记
     * @param unknown $planId
     */
    function del_plan( $planId ){
        $resp = new ResponePojo();
        if ( del_Plan_service( $planId ) ){
            $resp->put("msg", "删除{$planId}成功！");
            return returnValue($resp);
        }
        return returnValue($resp);
    }

    /***
     * 获取任务时间段
     * @param $planid 任务id
     */
    function planTimeSlot( $planid ){
        $resp = new ResponePojo();
        $result = planTimeSlot_server($planid);
        $planItem = array();
        while( $plan = $result->fetch_object() ){
            array_push($planItem,$plan);
        }
        if( !$result ){
            $resp->put("status",1);
            $resp->put("msg","查询失败！");
            return returnValue($resp);
        }
        $resp->put("time_slot",$planItem);
        return returnValue($resp);
    }

    /**
     * 查询指定任务
     * @param $planId
     */
    function getPlanByPlanId( $planid ){
        $resp = new ResponePojo();
        $result = getPlanById_server($planid);
        if( $result != null || $result != false ){
            $resp->put("plan",$result->fetch_object());
            return returnValue($resp);
        }
        $resp->put("status",1);
        $resp->put("msg","查询失败！");
        return returnValue($resp);
    }
    /**
     * 修改任务信息
     */
    function updatePlan( $plan_id,$planName,$budgetDate,$planInfo,$closing_date ){
        $resp = new ResponePojo();
        $plan = new Plan();
        $plan->plan_id = $plan_id;
        $plan->plan_name = $planName;
        $plan->budgetDate = $budgetDate;
        $plan->plan_info = $planInfo;
        $plan->closing_date = $closing_date;
        if( updatePlan_service($plan) ){
            $resp->put("status",0);
        }else{
            $resp->put("status",1);
            $resp->put("msg","修改失败！");
        }
        return returnValue($resp);
    }







    /*                                          ---强行分割---                                 */
    /**
     * 通用返回响应对象调用方法
     * @param $resp
     * @return mixed
     */
    function returnValue( $resp ){
        if( !is_span_domain() ){
            echo $resp->getJson();
        }
        return $resp->getJson();
    }

    /**
     * 判断当前请求是否是跨域请求
     */
    function is_span_domain(){
        $req_url = explode('/',$_SERVER['REQUEST_URI']);
        foreach( $req_url as $_url ){
            if( $_url == 'jsonp_api' ){
                //跨域请求
                return true;
            }
        }
        return false;
    }

?>
