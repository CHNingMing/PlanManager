<?php
    //连接对象
    static $conn;

    connDatabase();
    //连接数据库
    function connDatabase(){
        global $conn;
        
        $servername = "kqktrsb2.2364.dnstoo.com";
        $username = "ym970u_f";
        $password = "a203358";
        $conn = new mysqli($servername,$username,$password);
        if( $conn -> connect_errno ){
            echo "<h1>connection error!</h1>";
            return;
        }
    }
    //获取任务列表
    function getPlanItem(){
        global  $conn;
        $result = mysqli_query($conn, "SELECT plan_id,plan_name,budgetData FROM ym970u.plan_item;");
        if( mysqli_num_rows($result) > 0 ){
            while(  $row = mysqli_fetch_assoc($result) ){
                echo "<tr>";
                echo "<td>{$row['plan_name']}</td>";
                echo "<td>{$row['budgetData']}</td>";
                echo "<td><a href='#'>编辑</a></td>";
                echo "<td><a href='#'>删除</a></td>";
                echo "</tr>";
            }
        }
    }
?>


<html>
	<head>
		<title>任务列表</title>
	</head>
	<body>
		<!-- 搜索 -->
		<form style="display:initial;">
			任务名称: 
			<input type="text" name="planName" placeholder="请输入任务名称..." />
			任务类型:
			<select name="planType">
				<option value="curr_day" >今天</option>
				<option value="all_day" >全部</option>
			</select>
			<select name="planState">
				<option value="0">未开始</option>
				<option value="1">进行中</option>
				<option value="2">成功</option>
				<option value="3">失败</option>
				<option value="4">超时任务</option>
			</select>
		</form>
		<span>
			今天奋斗时间(分钟):10
			
		</span>
		<br />
		<table>
			<thead>
				<tr>
					<td>任务名称</td>
					<td>预计时间</td>
				</tr>
			</thead>
			<tbody>
				<?php
        		  getPlanItem();
        		?>
			</tbody>
		</table>
	</body>
</html>


