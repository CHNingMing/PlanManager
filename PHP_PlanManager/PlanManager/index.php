<?php
    require_once 'Conntroll/indexConntroll.php';
    connDatabase();
    $planTypeItem = array("curr_day"=>"今天","all_day"=>"全部");
    $planState = array(-1=>"全部",0=>"未开始",1=>"进行中",2=>"成功",3=>"失败");
    
    $paramType = "";
    $paramState = "";
    //init
    if( sizeof($_POST) > 0 ){
        $paramType = $_POST['planType'];
        $paramState = $_POST['planState'];
    }
    
?>

<!--
    后期全改成JSON传值
    
-->

<html>
	<head>
		<title>任务列表</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<!-- VUE -->
		<script type="text/javascript" src="js/vue.js"></script>
		<!-- Amaze UI -->
		<!-- <script type="text/javascript" src="js/amazeui.js"></script> -->
		<link rel="stylesheet" type="text/css" href="css/amazeui.css" />
		<link rel="stylesheet" type="text/css" href="css/project/index.css" />
		<script type="text/javascript" src="js/project/index.js"></script>
		<script type="text/javascript" src="js/vue-resource.js"></script>
	</head>
	<body>
	<div class="am-panel am-panel-default">
        <span id="app">
        	今天奋斗时间(分钟):{{ StruggleMin }}
        </span>
        <br />
        <!-- 搜索 -->
        <form method="post" style="display:initial;" id="searchForm" onsubmit="return false;" >
			任务名称:
			<div class="am-input-group am-input-group-primary">
				<span class="am-input-group-btn">
					<button class="am-btn am-btn-primary" type="button">
						<span class="am-icon-search"></span>
					</button>
				</span> 
				<input type="text" class="am-form-field" name="planName" placeholder="请输入任务名称..." />
			</div>
			
        		任务类型:
        		<select name="planType" class="am-form-select">
        			<?php 
        			     foreach( $planTypeItem as $key => $value ){
        			         echo "<option value='{$key}' ".($key == $paramType ? "selected" : "").">$value</option>";  
            	         }
        			?>
        		</select>
        		<select name="planState" class="am-form-select">
        		    <?php 
        		         foreach( $planState as $key => $value ){
            	               echo "<option value='{$key}' ".($key == $paramState ? "selected" : "")." >$value</option>";  
            	         }
        			?>
        		</select>
        		
        		<button class="am-btn am-btn-default am-radius am-icon-check" onclick="searchPlanList()" >搜索</button>
        		
        </form>
        <button class="am-btn am-btn-default am-radius am-icon-search" onclick="location.href='createNewPlan.php'" >新建任务</button>
        <br />
		<table id="_table"  border="1" class="am-table am-table-bordered am-table-radius am-table-striped am-table-hover">
			<thead>
				<tr>
					<td >任务名称</td>
					<td>预计时间</td>
					<td>任务状态</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
			
				<tr v-if="tables_data_display" v-for="table_data in tables_data">
					<td>{{table_data.plan_name}}</td>
					<td>{{table_data.budgetDate}}</td>
					<td v-html="table_data.plan_state"></td>
					<td>
						<a v-bind:planid="table_data.plan_id" href="#">编辑</a> | 
						<a v-bind:planid="table_data.plan_id" href="#">删除</a>
					</td>
				</tr>
				<tr v-if="!tables_data_display">
					<td colspan="4">没有任务记录！</td>
				</tr>
			
        	</tbody>
		</table>
	</div>
	</body>
</html>
<script>

	window.onload = function(){
		//当前奋斗时间
		new Vue({
			el: '#app',
			data : {
				StruggleMin: '<?php currDayTime() ?>'
			}
		});
		var vue = new Vue({
			el: '#_table',
			data:{
				tables_data: new Array(),
				tables_data_display: true
			}
		});
		searchPlanList();
	}
	


	function searchPlanList(){
		var searchForm = document.getElementById('searchForm');
		//取出vue对象
		var vue = document.getElementById('_table').__vue__;
		//任务列表
		var reqlen = <?php echo sizeof($_POST) ?>;
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
						span_str+="title='点击开始任务' class='am-badge am-radius' <a class='plan_state' onclick='updatePlanState("+data.plan_id+",1)' href='#'>未开始";
					break;
					case "1":
						span_str+="title='点击关闭任务' class='am-badge am-badge-secondary am-radius'><a class='plan_state'  href='#'>进行中";
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
	var table_datas ;
</script>


