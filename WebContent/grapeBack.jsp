<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
		<link rel="stylesheet" type="text/css" href="./css/jquery.modal.css">
		<link rel="stylesheet" type="text/css" href="./css/checkUp.css">
		<script type="text/javascript" src="./js/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="./js/highcharts.js"></script>
		<script type="text/javascript" src="./js/modules/data.js"></script>
		<script type="text/javascript" src="./js/modules/exporting.js"></script>
		<script type="text/javascript" src="./js/grape.js"></script>
		<link rel="stylesheet" type="text/css" href="./css/grape.css">
		<script src="http://cdn.jsdelivr.net/webshim/1.12.4/extras/modernizr-custom.js"></script>
		<script src="http://cdn.jsdelivr.net/webshim/1.12.4/polyfiller.js"></script>
		<script>
		  webshims.setOptions('waitReady', false);
		  webshims.setOptions('forms-ext', {types: 'date'});
		  webshims.polyfill('forms forms-ext');
		</script>
		<script type="text/javascript" src = "./js/jquery.modal.js"></script>
		<script type="text/javascript" src = "./js/regularCheckUp.js"></script>
	<title>�׷��� ���</title>
	</head>
	<body>
		<div id = "selectLayout">
			<div class = "group">
				<span>���� �׷�</span>
			</div>
			<div class = "inputPlantInfo">
<!-- 				��� ���κ� -->
				<div style = "display:none;" id = "searchCoInfo">
					<div>ȸ���</div>
					<div>
						<select id = "searchCo"></select>
					</div>
					<div>������</div>
					<div>
						<select id = "searchPlant" disabled="disabled"></select>
					</div>
					<div>������</div>
					<div>
						<select id = "searchGenerator" disabled="disabled"></select>
					</div>
					<div style = "float: right; width: width : 80px;">
						<a href = "#close-model" rel = "modal:close"><button onclick = "insertInput()">����</button></a>
					</div>
				</div>
<!-- 				��� ���κ� -->
				<div class ="textLayout">��</div><div><a href ="#searchCoInfo" rel = "modal:open"><input readonly = "readonly" type="text"  class = "coGetInfo" placeholder="ex)��������" id = "co"></a></div>
				<div class ="textLayout">������</div><div><a href ="#searchCoInfo" rel = "modal:open"><input readonly = "readonly" type="text" class = "coGetInfo" placeholder="ex)����ȭ�¹���ó" id = "plant"></a></div>
				<div class ="textLayout">������</div><div><a href ="#searchCoInfo" rel = "modal:open"><input readonly = "readonly" type="text" class = "coGetInfo" id = "generator"></a><input type ="hidden" id = "generatorCode"></div>
			</div>
			<div class = "checkName">
				<div class = "textLayout">�����</div><div><input type = "text" id = "checkName"></div>
			</div>
			<div class = "classInfo">
				<div class = "textLayout">��������</div>
				<div style = "display:none;" id = "classSearch">
					<span></span>
					<select>
					</select>
					<a href = "#close-model" rel = "modal:close"><button onclick = "select()">����</button></a>
				</div>
				<div><a href="#classSearch" rel ="modal:open"><input type = "text" class = "levelClass" readonly = "readonly" placeholder="Ŭ���Ͽ� ã�� �ּ���"></a><input type ="hidden" class ="levelEquipCode" ></div>
				<div><a href="#classSearch" rel ="modal:open"><input type = "text" class = "levelClass" readonly = "readonly" placeholder="Ŭ���Ͽ� ã�� �ּ���"></a><input type ="hidden" class ="levelEquipCode" ></div>
				<div><a href="#classSearch" rel ="modal:open"><input type = "text" class = "levelClass" readonly = "readonly" placeholder="Ŭ���Ͽ� ã�� �ּ���"></a><input type ="hidden" class ="levelEquipCode" ></div>
				<div><a href="#classSearch" rel ="modal:open"><input type = "text" class = "levelClass" readonly = "readonly" placeholder="Ŭ���Ͽ� ã�� �ּ���"></a><input type ="hidden" class ="levelEquipCode" ></div>
				<div><a href="#classSearch" rel ="modal:open"><input type = "text" class = "levelClass" readonly = "readonly" placeholder="Ŭ���Ͽ� ã�� �ּ���"></a><input type ="hidden" class ="levelEquipCode" ></div>
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
		<div id = "grapePrint">
		</div>
		
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