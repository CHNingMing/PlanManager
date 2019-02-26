/**
 * 修饰工具
 */
function searchPlan(  ){
	
}



function getRequest(url, params,fun_succ,fun_err) {
	  return new Promise((resolve, reject) => {
	    Vue.http.get(
	      url,
	      {
	        params: params
	      },
	      {emulateJSON: true}
	    )
	    .then((res) => {   //成功的回调
	    	if( fun_succ != undefined ){
	    		 fun_succ(res);
	    	}
	     
	    })
	    .catch((res) => {  //失败的回调
	    	if( fun_err != undefined ){
	    		fun_err(res);
	    	}
	      
	    });
	});
}

/**
 * 打开窗口
 * @param winid 窗口id
 * @param even_dom 传入dom,取dom下 f_ 开头属性添加到窗口中
 * @param fun 打开窗口后出发函数，参数：( 窗口DOM , 按钮DOM )
 * @returns
 */
function openWin(winid,even_dom,fun){
	var window = document.getElementById(winid);
	window.style.display = 'block';
/*	if( even_dom != undefined && even_dom != null ){
		window.even_dom = even_dom;
	}*/
	if( even_dom != undefined && even_dom != null ){
		for( var attri of even_dom.attributes ){
			var attr_str = attri.name;
			//约定只会传f_开头属性
			if( attr_str[0]=="f" && attr_str[1]=="_" ){
				window.setAttribute(attri.name,attri.value);
			}
		}
	}
	//注册监听ESC事件
	document.onkeyup = function(e){
		if( e.keyCode == 27 ){
			closeWin(winid);
		}
	}
	//执行自定义函数
	if( fun != undefined && fun != null ){
		fun(window,even_dom);
	}
}
function closeWin(winid){
	var window = document.getElementById(winid);
	window.style.display = 'none';
	document.onkeyup = undefined;
}
//取出 form 内容
function getFormParam(formId){
	var form = document.getElementById(formId);
	if( form == null ){
		console.log('没有找到form!');
		return;
	}
	var param = {};
	var doms = form.querySelectorAll('input,select,textarea');
	for( var dom of doms ){
		param[dom.name] = dom.value;
	}
	return param;
}
//清空 form 内容
function clearForm(formId){
	var form = document.getElementById(formId);
	form.reset();
}
  
/**
 * 主页部分
 */
//获取任务列表
function searchPlanList(){
    var searchForm = document.getElementById('searchForm');
    //取出vue对象
    var vue = document.getElementById('_table').__vue__;
    //任务列表
//		var reqlen = <?php echo sizeof($_POST) ?>;
    var param = {};
    var doms = searchForm.querySelectorAll('input,select');
    for( var dom of doms ){
        param[dom.name] = dom.value;
    }
    /*
    if( reqlen > 0 ){
        param.planType = '<?php echo $_POST["planType"] ?>';
        param.planState = '<?php echo $_POST["planState"] ?>';
        param.planName = '<?php echo $_POST['planName']?>';
    }
    */
    getRequest('json_api/getPlanList',param,function(data){
        //请求数组
        var table_data = data.body;
        //vue维护数组
        var datas = vue.tables_data;
        if( table_data.status == 1 ){
            vue.tables_data.splice(0);
        }
        for( var data of table_data.planList ){
            var span_str = "<span planid='"+data.plan_id+"' ";
            switch( data.plan_state ){
                case "0":
                    span_str+="title='点击开始任务' class='am-badge am-radius'> <a class='plan_state' onclick='updatePlanState("+data.plan_id+",1)' href='#'>未开始";
                    break;
                case "1":
                    span_str+="title='点击关闭任务' class='am-badge am-badge-secondary am-radius'><a class='plan_state' onclick='updatePlanState("+data.plan_id+",2)'  href='#'>进行中";
                    break;
                case "2":
                    span_str+="class='am-badge am-badge-success am-radius'>成功";
                    break;
            }
            span_str+="</a></span>";
            data.plan_state = span_str;
        }
        vue.tables_data = table_data.planList;
    });
}

//搜索任务方法
function search_plan(plan_name){
	postReq();
}
//创建任务
function createPlan(){
	var createPlanForm = document.querySelector('.form_table');
	param = getFormParam('crePlanForm');
	getRequest('json_api/createPlan',param,function(data){
		clearForm('crePlanForm');
		closeWin('createPlanWin');
		searchPlanList();
	});
}
/* 删除任务 */
function del_plan_ready( window,btnDom ){
    var msg = window.querySelector('#msg');
    msg.innerHTML = "是否删除 <span style='color:red;'>"+btnDom.getAttribute('f_planname')+"</span>";
}
function del_plan(){
    var windows = document.getElementById('message_win_del');
    var plan_id = windows.attributes.f_planid.value;
    getRequest('json_api/del_plan',{planId:plan_id},function(data){
        if( data.body.status == 0 ){
            closeWin('message_win_del');
            searchPlanList();
        }
    });
}
/* 删除任务 END */
/**
 * 更新任务状态
 * @param $planId
 * @param $plan_state
 * @returns
 */
function updatePlanState( $planId,$plan_state ){
	getRequest('json_api/updateState_api',{
		plan_id:$planId,
		plan_state:$plan_state
	},function(data){
		if( data.body.status == 0 ){
			searchPlanList();
		}
	});
	
} 