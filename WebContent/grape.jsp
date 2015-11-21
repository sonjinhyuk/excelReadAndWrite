<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
		<script type="text/javascript" src="./js/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="./js/highcharts.js"></script>
		<script type="text/javascript" src="./js/modules/data.js"></script>
		<script type="text/javascript" src="./js/modules/exporting.js"></script>
		<script type="text/javascript" src="./js/grape.js"></script>
		<link rel="stylesheet" type="text/css" href="./css/grape.css">
		<script src="http://cdn.jsdelivr.net/webshim/1.12.4/extras/modernizr-custom.js"></script>
		<!-- polyfiller file to detect and load polyfills -->
		<script src="http://cdn.jsdelivr.net/webshim/1.12.4/polyfiller.js"></script>
		<script>
		  webshims.setOptions('waitReady', false);
		  webshims.setOptions('forms-ext', {types: 'date'});
		  webshims.polyfill('forms forms-ext');
		</script>
	<title>�׷��� ���</title>
	</head>
	<body>
		<div id = "selectLayout">
			<div class ="powerPlantSelect">
				���±׷��: <input type = "text" id = "plantCo" class = "">&nbsp; ������: <input type="text" id = "plant" >  &nbsp; ������: <input type="text" id = "generator" >
			</div>
			<div class ="levelSelect">
				<div>1����</div>
				<div><input type="text" id = "_1level"></div>
				<button class = "search">ã��</button>
			</div>
			<div class ="levelSelect">
				<div>2����</div>
				<div><input type="text" id = "_1level"></div>
				<button class = "search">ã��</button>
			</div>
			<div class ="levelSelect">
				<div>3����</div>
				<div><input type="text" id = "_1level"></div>
				<button class = "search">ã��</button>
			</div>
			<div class ="levelSelect">
				<div>4����</div>
				<div><input type="text" id = "_1level"></div>
				<button class = "search">ã��</button>
			</div>
			<div class = "lebelSelect periodSelect">
				<select id = "periodDate">
					<option value = 1 >1��</option>
					<option value = 0.5>6����</option>
					<option value = 3 >3��</option>
					<option value = 5 >5��</option>
					<option value = 'period' >�Ⱓ ����</option>
				</select>
				<input type = "date" id = "startDate" class = "date" disabled="disabled">
				<input type = "date" id = "endDate" class = "date" disabled="disabled">
				<button id = "drawGrape">�׷���</button>
			</div>
		</div>
<!-- 		<div id = "grapePrint"> -->
			<img src='' usemap="#map1" id = "printGrape" border="0">
			<map name="map1" id = "map1"></map>
<!-- 		</div> -->
		<div class = "listClass">
			<div class = "list">
				����Ʈ1
				<div></div>
			</div>
			<div class = "list">
				����Ʈ2
				<div></div>
			</div>
			<div class = "list">
				����Ʈ3
				<div></div>
			</div>
			<div class = "list">
				����Ʈ4
				<div></div>
			</div>
		</div>
	</body>
</html>