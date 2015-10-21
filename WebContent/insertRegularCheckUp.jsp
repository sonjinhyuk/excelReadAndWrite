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
		<title>정비점검 데이터 입력</title>
	</head>
	<body>
		<div class = "inputLayout">
			사후 정비 정보 입력
			<div class = "radioButton">
				<input type = "radio" name = "inputFile" id = "basic" value = "basic"> <label for = "basic">기본 입력</label>
				<input type = "radio" name = "inputFile" id = "excel" value = "excel"> <label for = "excel">Excel 파일 입력</label>
				<input type = "radio" name = "inputFile" id = "XML"   value = "XML">   <label for = "XML">XML파일 입력</label>
			</div>
			<div class = "group">
				<span>전력 그룹</span>
			</div>
			<div class = "inputPlantInfo">
				<div class ="textLayout">사</div><div><input type="text" placeholder="ex)남동발전" id = "co"></div>
				<div class ="textLayout">발전소</div><div><input type="text" placeholder="ex)여수화력발전처" id = "plant"></div>
				<div class ="textLayout">발전기</div><div><input type="text" id = "generator"><button>찾기</button></div>
			</div>
			<div class = "checkName">
				<div class = "textLayout">정비명</div><div><input type = "text" id = "checkName"></div>
			</div>
			<div class = "classInfo">
				<div class = "textLayout">계층정보</div>
				<div><input type = "text" class = "class"> <button class = "classSearch">찾기</button></div><!-- 	첫 번째 클레스인지 아닌지 확인 -->
				<div><input type = "text" class = "class"> <button class = "classSearch">찾기</button></div>
				<div><input type = "text" class = "class"> <button class = "classSearch">찾기</button></div>
				<div><input type = "text" class = "class"> <button class = "classSearch">찾기</button></div>
				<div><input type = "text" class = "class"> <button class = "classSearch">찾기</button></div>
			</div>
			<div style="border-bottom : 1px solid black;">실적 입력일</div>
			<div class = "dateInput">
				<div class = "textLayout">시</div><div><input type = "date" id = "inputResultDate"><input type = "time" id = "inputResultTime"></div>
				<div class = "textLayout">처리시간</div><input type = "date" id = "startDate"><input type = "time" id = "startTime"> ~ <input type = "date" id = "endDate" ><input type = "time" id = "endTime"> 
			</div>
			<div style="border-bottom : 1px solid black;">고장코드</div>
			<div class = "fault">
				<div class = "faultInfo">
					<div>고장부위(작업부위)</div>
					<div>고장모드(내용)</div>
					<div>고장원인</div>
					<div>예방(정비)업무</div>
					<div>삭제</div>
				</div>
				<div class = "inputfault">
					<div><input type = "text" id = "faultLocation" class = "faultSearch"><button>찾기</button><input type ="text" id = "faultContent" class = "faultContent"></div>
					<div><input type = "text" id = "faultLocation" class = "faultSearch"><button>찾기</button><input type ="text" id = "faultContent" class = "faultContent"></div>
					<div><input type = "text" id = "faultLocation" class = "faultSearch"><button>찾기</button><input type ="text" id = "faultContent" class = "faultContent"></div>
					<div><input type = "text" id = "faultLocation" class = "faultSearch"><button>찾기</button><input type ="text" id = "faultContent" class = "faultContent"></div>
					<div><button>삭제</button></div>
				</div>
			</div>
			<div class = "workContent">
				<div class = "textLayout">작업 내용</div><div><textarea id = "workContent"></textarea></div>
				<div class = "textLayout">차기장비반영사항</div><div><textarea id = "nextCheckReflection"></textarea></div>
			</div>
		</div>
	</body>
</html>