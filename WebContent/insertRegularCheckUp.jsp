<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
		<link rel="stylesheet" type="text/css" href="./css/checkUp.css">
		<link rel="stylesheet" type="text/css" href="./css/jquery.modal.css">
		<script type="text/javascript" src="./js/jquery-1.11.3.min.js"></script>
		<script src="http://cdn.jsdelivr.net/webshim/1.12.4/extras/modernizr-custom.js"></script>
		<script src="http://cdn.jsdelivr.net/webshim/1.12.4/polyfiller.js"></script>
		<script>
			webshims.setOptions('waitReady', false);
			webshims.setOptions('forms-ext', {types: 'date'});
			webshims.polyfill('forms forms-ext');
		</script>
		<script type="text/javascript" src = "./js/jquery.modal.js"></script>
		<script type="text/javascript" src = "./js/regularCheckUp.js"></script>
		<title>�������� ������ �Է�</title>
	</head>
	<body>
		<div class = "inputLayout">
			���� ���� ���� �Է�
			<div class = "radioButton">
				<input type = "radio" name = "inputFile" id = "basic" value = "basic" checked="checked"> <label for = "basic">�⺻ �Է�</label>
				<input type = "radio" name = "inputFile" id = "excel" value = "excel"> <label for = "excel">Excel ���� �Է�</label>
				<input type = "radio" name = "inputFile" id = "XML"   value = "XML">   <label for = "XML">XML���� �Է�</label>
			</div>
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
			<div style="border-bottom : 1px solid black;">���� �Է���</div>
			<div class = "dateInput">
				<div class = "textLayout">��</div><div><input type = "date" id = "inputResultDate"><input type = "time" id = "inputResultTime"></div>
				<div class = "textLayout">ó���ð�</div><input type = "date" id = "startDate"><input type = "time" id = "startTime"> ~ <input type = "date" id = "endDate" ><input type = "time" id = "endTime"> 
			</div>
			<div style="border-bottom : 1px solid black;">�����ڵ�<span style="float:right;"><button>�߰�</button></span></div>
			<div class = "fault">
				<div class = "faultInfo">
					<div>�������(�۾�����)</div>
					<div>������(����)</div>
					<div>�������</div>
					<div>����(����)����</div>
					<div>����</div>
				</div>
				<div class = "inputfault">
					<div><input type = "text" id = "faultLocation" class = "faultSearch"><button>ã��</button><input type ="text" id = "faultContent" class = "faultContent"></div>
					<div><input type = "text" id = "faultLocation" class = "faultSearch"><button>ã��</button><input type ="text" id = "faultContent" class = "faultContent"></div>
					<div><input type = "text" id = "faultLocation" class = "faultSearch"><button>ã��</button><input type ="text" id = "faultContent" class = "faultContent"></div>
					<div><input type = "text" id = "faultLocation" class = "faultSearch"><button>ã��</button><input type ="text" id = "faultContent" class = "faultContent"></div>
					<div><button>����</button></div>
				</div>
			</div>
			<div class = "workContent">
				<div class = "textLayout">�۾� ����</div><div><textarea id = "workContent"></textarea></div>
				<div class = "textLayout">�������ݿ�����</div><div><textarea id = "nextCheckReflection"></textarea><button onclick="checkUp()">Ȯ��</button></div>
			</div>
			<div class = "workContent checkUpType">
				���� Ÿ��<br/>
			</div>
		</div>
	</body>
</html>