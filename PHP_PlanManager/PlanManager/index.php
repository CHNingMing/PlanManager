<?php
    require_once 'Conntroll/indexConntroll.php';
    connDatabase();
    
    $planTypeItem = array("curr_day"=>"今天","all_day"=>"全部");
    $planState = array(-1=>"全部",0=>"未开始",1=>"进行中",2=>"成功",3=>"失败");
?>


<html>
	<head>
		<title>任务列表</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<!-- VUE -->
		<script type="text/javascript" src="js/vue.js"></script>
		<!-- Amaze UI -->
		<!-- <script type="text/javascript" src="js/amazeui.js"></script> -->
		<link rel="stylesheet" type="text/css" href="css/amazeui.css" />
		<link rel="stylesheet" type="text/css" href="css/index.css" />
		
	</head>
	<body>
	<div class="am-panel am-panel-default">
        <span id="app">
        	今天奋斗时间(分钟):{{ StruggleMin }}
        </span>
        <br />
        <!-- 搜索 -->
        <form method="post" style="display:initial;" >
        		任务名称: 
        		<input type="text" class="am-form-field am-round" name="planName" placeholder="请输入任务名称..." />
        	任务类型:
        		<select name="planType" class="am-form-select">
        			<option value="curr_day" >今天</option>
        			<option value="all_day" selected >全部</option>
        		</select>
        		<select name="planState" class="am-form-select">
        			<option value="-1">全部</option>
        			<option value="0">未开始</option>
        			<option value="1">进行中</option>
        			<option value="2">成功</option>
        			<option value="3">失败</option>
        			<option value="4">超时任务</option>
        		</select>
        		<input type="submit" class="am-btn am-btn-default am-radius am-icon-search" value="提交" />
        </form>
        <button onclick="location.href='createNewPlan.php'" >新建任务</button>
        <br />
		<table border="1" class="am-table am-table-bordered am-table-radius am-table-striped am-table-hover">
			<thead>
				<tr>
					<td >任务名称</td>
					<td>预计时间</td>
					<td>任务状态</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
        			<?php
                        getPlanItem();
                    ?>
        	</tbody>
		</table>
	</div>
	</body>
</html>
<script>
	
	new Vue({
		el: '#app',
		data : {
			StruggleMin: '<?php currDayTime() ?>'
		}
	});

	
	//搜索任务方法
	function search_plan(plan_name){
		postReq();
	}

	//封装post请求
	
	
</script>


