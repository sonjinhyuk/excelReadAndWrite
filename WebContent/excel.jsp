<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
		<script type="text/javascript" src="./js/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="./js/jquery.form.js"></script>
		<script type="text/javascript" src="./js/excel.js"></script>
		<link rel="stylesheet" type="text/css" href="./css/excel.css">
		<title>엑셀</title>
	</head>
	<body>
		<form id = "#submitForm" enctype="multipart-form-data" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
			<input name="attachFile" id="attachFile" type="file">
			<button type="button" id="submitBtn">저장하기</button>
		</form>
		<select id = "tableSelect">
		</select>
		<div id = "dataBaseTable">
		</div>
		<div id = "excelTable">
		</div>	
		<button onclick="insertDBData()" id = "saveButton" >저장하기</button>
	</body>
</html>