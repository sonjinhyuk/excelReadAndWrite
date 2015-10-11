<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
		<script type="text/javascript" src="./js/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="./js/jquery.form.js"></script>
		<script type="text/javascript" src="./js/grape.js"></script>
		<link rel="stylesheet" type="text/css" href="./css/grape.css">
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
				<button>찾기</button>
			</div>
			<div class ="levelSelect">
				<div>2레벨</div>
				<div><input type="text" id = "_1level"></div>
				<button>찾기</button>
			</div>
			<div class ="levelSelect">
				<div>3레벨</div>
				<div><input type="text" id = "_1level"></div>
				<button>찾기</button>
			</div>
			<div class ="levelSelect">
				<div>4레벨</div>
				<div><input type="text" id = "_1level"></div>
				<button>찾기</button>
			</div>
		</div>
		
		<div id = "listClass">
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