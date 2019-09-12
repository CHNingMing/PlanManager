<?php
    require_once('Conntroll/indexConntroll.php');
    connDatabase();
    createPlan();
    
    
?>

<html>
	<head>
		<title>编辑任务</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<!-- VUE -->
		<script type="text/javascript" src="js/vue.js"></script>
		<!-- Amaze UI -->
		<!-- <script type="text/javascript" src="js/amazeui.js"></script> -->
		<link rel="stylesheet" type="text/css" href="css/amazeui.css" />
		<link rel="stylesheet" type="text/css" href="css/index.css" />
		</head>
	<body>
		<form>
			<table>
				<tr>
					<td>任务名称:</td>
					<td>
						<input class="am-form-field am-round" name="planName" />
					</td>
				</tr>
				<tr>
					<td>预计完成时间:</td>
					<td>
						<input class="am-form-field am-round" name="budgetDate" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" class="am-btn am-btn-default am-radius am-icon-search" value="保存" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>