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
	<title>그래프 출력</title>
	</head>
	<body>
		<div id = "selectLayout">
			<div class ="powerPlantSelect">
				전력그룹사: <input type = "text" id = "plantCo" class = "">&nbsp; 발전소: <input type="text" id = "plant" >  &nbsp; 발전기: <input type="text" id = "generator" >
			</div>
			<div class ="levelSelect">
				<div>1레벨</div>
				<div><input type="text" id = "_1level"></div>
				<button class = "search">찾기</button>
			</div>
			<div class ="levelSelect">
				<div>2레벨</div>
				<div><input type="text" id = "_1level"></div>
				<button class = "search">찾기</button>
			</div>
			<div class ="levelSelect">
				<div>3레벨</div>
				<div><input type="text" id = "_1level"></div>
				<button class = "search">찾기</button>
			</div>
			<div class ="levelSelect">
				<div>4레벨</div>
				<div><input type="text" id = "_1level"></div>
				<button class = "search">찾기</button>
			</div>
			<div class = "lebelSelect periodSelect">
				<select id = "periodDate">
					<option value = 1 >1년</option>
					<option value = 0.5>6개월</option>
					<option value = 3 >3년</option>
					<option value = 5 >5년</option>
					<option value = 'period' >기간 선택</option>
				</select>
				<input type = "date" id = "startDate" class = "date" disabled="disabled">
				<input type = "date" id = "endDate" class = "date" disabled="disabled">
				<button id = "drawGrape">그래프</button>
			</div>
		</div>
<!-- 		<div id = "grapePrint"> -->
			<img src='' usemap="#map1" id = "printGrape" border="0">
			<map name="map1" id = "map1"></map>
<!-- 		</div> -->
		<div class = "listClass">
			<div class = "list">
				리스트1
				<div></div>
			</div>
			<div class = "list">
				리스트2
				<div></div>
			</div>
			<div class = "list">
				리스트3
				<div></div>
			</div>
			<div class = "list">
				리스트4
				<div></div>
			</div>
		</div>
	</body>
</html>