<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
		<script type="text/javascript" src="./js/jquery-1.11.3.min.js"></script>
		<link rel="stylesheet" type="text/css" href="./css/checkUp.css">
		<script src="http://cdn.jsdelivr.net/webshim/1.12.4/extras/modernizr-custom.js"></script>
		<script src="http://cdn.jsdelivr.net/webshim/1.12.4/polyfiller.js"></script>
		<script>
		  webshims.setOptions('waitReady', false);
		  webshims.setOptions('forms-ext', {types: 'date'});
		  webshims.polyfill('forms forms-ext');
		</script>
		<title>�������� ������ �Է�</title>
	</head>
	<body>
		<div class = "inputLayout">
			���� ���� ���� �Է�
			<div class = "radioButton">
				<input type = "radio" name = "inputFile" id = "basic" value = "basic"> <label for = "basic">�⺻ �Է�</label>
				<input type = "radio" name = "inputFile" id = "excel" value = "excel"> <label for = "excel">Excel ���� �Է�</label>
				<input type = "radio" name = "inputFile" id = "XML"   value = "XML">   <label for = "XML">XML���� �Է�</label>
			</div>
			<div class = "group">
				<span>���� �׷�</span>
			</div>
			<div class = "inputPlantInfo">
				<div class ="textLayout">��</div><div><input type="text" placeholder="ex)��������" id = "co"></div>
				<div class ="textLayout">������</div><div><input type="text" placeholder="ex)����ȭ�¹���ó" id = "plant"></div>
				<div class ="textLayout">������</div><div><input type="text" id = "generator"><button>ã��</button></div>
			</div>
			<div class = "checkName">
				<div class = "textLayout">�����</div><div><input type = "text" id = "checkName"></div>
			</div>
			<div class = "classInfo">
				<div class = "textLayout">��������</div>
				<div><input type = "text" class = "class"> <button class = "classSearch">ã��</button></div><!-- 	ù ��° Ŭ�������� �ƴ��� Ȯ�� -->
				<div><input type = "text" class = "class"> <button class = "classSearch">ã��</button></div>
				<div><input type = "text" class = "class"> <button class = "classSearch">ã��</button></div>
				<div><input type = "text" class = "class"> <button class = "classSearch">ã��</button></div>
				<div><input type = "text" class = "class"> <button class = "classSearch">ã��</button></div>
			</div>
			<div style="border-bottom : 1px solid black;">���� �Է���</div>
			<div class = "dateInput">
				<div class = "textLayout">��</div><div><input type = "date" id = "inputResultDate"><input type = "time" id = "inputResultTime"></div>
				<div class = "textLayout">ó���ð�</div><input type = "date" id = "startDate"><input type = "time" id = "startTime"> ~ <input type = "date" id = "endDate" ><input type = "time" id = "endTime"> 
			</div>
			<div style="border-bottom : 1px solid black;">�����ڵ�</div>
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
				<div class = "textLayout">�������ݿ�����</div><div><textarea id = "nextCheckReflection"></textarea></div>
			</div>
		</div>
	</body>
</html>