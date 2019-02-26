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
        		
        		<button class="am-btn am-btn-default am-radius am-icon-search" onclick="searchPlanList()" >搜索</button>
        		
        </form>
        <button class="am-btn am-btn-default am-radius am-icon-check" onclick="openWin('createPlanWin')" >新建任务</button>
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
						<a v-bind:f_planid="table_data.plan_id" onclick="openWin('editplan_win',this,plan_edit_ready)" href="#">编辑</a> |
						<a v-bind:f_planid="table_data.plan_id" v-bind:f_planname="table_data.plan_name" onclick="openWin('message_win_del',this,del_plan_ready)" href="#">删除</a>
					</td>
				</tr>
				<tr v-if="!tables_data_display">
					<td colspan="4">没有任务记录！</td>
				</tr>
			
        	</tbody>
		</table>
	</div>
	
	<!-- 窗口部分 -->
    <!-- 删除提示窗口 -->
	<div class="message_win" id="message_win_del">
		<table>
			<tr>
				<td><span id="msg">是否删除？</span></td>
			</tr>
			<tr>
				<td>
					<button class="am-btn am-btn-default am-radius am-icon-check" onclick="del_plan()" >是</button>
				</td>
				<td>
					<button class="am-btn am-btn-default am-radius am-icon-close" onclick="closeWin('message_win_del')" >不删</button>
				</td>
			</tr>
		</table>
		<br />
	</div>
	<!-- 添加任务窗口 -->
	<div id="createPlanWin" class="close">
    	<!-- 模糊层 -->
    	<div class="win_vague"></div>
    	<div class="window">
    		<!-- 标题 -->
    		<div class="title">
    			创建任务
    			<a href="#" class="closeBtn" onclick="closeWin('createPlanWin')" >X</a>
    		</div>
    		<!-- 正文 -->
    		<div class="context">
    		<form id="crePlanForm" onsubmit="return false;" >
    			<table class="form_table">
                	<tr>
                		<td>任务名称：</td>
                		<td>
                			<input name="planName" class="am-form-field" textarea="请输入任务名称..." />
                		</td>
                	</tr>
                	<tr>
                		<td>预计时间：</td>
                		<td>
                			<input name="budgetDate" class="am-form-field" />
                		</td>
                	</tr>
                	<tr>
                		<td colspan='2' style="text-align:center;">
                			<button class="am-btn am-btn-default am-radius am-icon-check"  style="margin-top:5%;width:100%;" onclick="createPlan()" >创建</button>
                		</td>
                	</tr>
                </table>
            </form>
    		</div>
    	</div>
	</div>
    <!-- 添加任务窗口 END -->

    <!-- 编辑列表窗口 -->
    <div id="editplan_win" class="close">
        <div class="win_vague"></div>
        <div class="window">
            <div class="title">
                任务编辑
                <a href="#" class="closeBtn" onclick="closeWin('editplan_win')" >X</a>
            </div>
            <div class="context">
                <div id="planDateList">
                    <table v-for="">
                        <tr>
                            <td>开始时间:</td>
                            <td>2019-02-01 10:10:00</td>
                            <td> &nbsp; | &nbsp; </td>
                            <td>结束时间:</td>
                            <td>2019-02-10 10:10:00</td>
                        </tr>
                        <tr>
                            <td colspan="5">
                                <div class="am-progress am-progress-striped">
                                    <div class="am-progress-bar am-progress-bar-secondary" style="width: 30%">耗时:30分/100分</div>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!-- 编辑列表窗口 END -->

	<!-- 窗口部分 END -->
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


    /* 编辑任务 */
    function plan_edit_ready(window){
        var plan_id = window.getAttribute('f_planid');



    }

    /* 编辑任务 END */

</script>


