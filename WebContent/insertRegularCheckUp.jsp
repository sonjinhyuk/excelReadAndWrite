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
		<title>정비점검 데이터 입력</title>
	</head>
	<body>
		<div class = "inputLayout">
			사후 정비 정보 입력
			<div class = "radioButton">
				<input type = "radio" name = "inputFile" id = "basic" value = "basic" checked="checked"> <label for = "basic">기본 입력</label>
				<input type = "radio" name = "inputFile" id = "excel" value = "excel"> <label for = "excel">Excel 파일 입력</label>
				<input type = "radio" name = "inputFile" id = "XML"   value = "XML">   <label for = "XML">XML파일 입력</label>
			</div>
			<div class = "group">
				<span>전력 그룹</span>
			</div>
			<div class = "inputPlantInfo">
<!-- 				모달 사용부분 -->
				<div style = "display:none;" id = "searchCoInfo">
					<div>회사명</div>
					<div>
						<select id = "searchCo"></select>
					</div>
					<div>발전소</div>
					<div>
						<select id = "searchPlant" disabled="disabled"></select>
					</div>
					<div>발전기</div>
					<div>
						<select id = "searchGenerator" disabled="disabled"></select>
					</div>
					<div style = "float: right; width: width : 80px;">
						<a href = "#close-model" rel = "modal:close"><button onclick = "insertInput()">저장</button></a>
					</div>
				</div>
<!-- 				모달 사용부분 -->
				<div class ="textLayout">사</div><div><a href ="#searchCoInfo" rel = "modal:open"><input readonly = "readonly" type="text"  class = "coGetInfo" placeholder="ex)남동발전" id = "co"></a></div>
				<div class ="textLayout">발전소</div><div><a href ="#searchCoInfo" rel = "modal:open"><input readonly = "readonly" type="text" class = "coGetInfo" placeholder="ex)여수화력발전처" id = "plant"></a></div>
				<div class ="textLayout">발전기</div><div><a href ="#searchCoInfo" rel = "modal:open"><input readonly = "readonly" type="text" class = "coGetInfo" id = "generator"></a><input type ="hidden" id = "generatorCode"></div>
			</div>
			<div class = "checkName">
				<div class = "textLayout">정비명</div><div><input type = "text" id = "checkName"></div>
			</div>
			<div class = "classInfo">
				<div class = "textLayout">계층정보</div>
				<div style = "display:none;" id = "classSearch">
					<span></span>
					<select>
					</select>
					<a href = "#close-model" rel = "modal:close"><button onclick = "select()">선택</button></a>
				</div>
				<div><a href="#classSearch" rel ="modal:open"><input type = "text" class = "levelClass" readonly = "readonly" placeholder="클릭하여 찾아 주세요"></a><input type ="hidden" class ="levelEquipCode" ></div>
				<div><a href="#classSearch" rel ="modal:open"><input type = "text" class = "levelClass" readonly = "readonly" placeholder="클릭하여 찾아 주세요"></a><input type ="hidden" class ="levelEquipCode" ></div>
				<div><a href="#classSearch" rel ="modal:open"><input type = "text" class = "levelClass" readonly = "readonly" placeholder="클릭하여 찾아 주세요"></a><input type ="hidden" class ="levelEquipCode" ></div>
				<div><a href="#classSearch" rel ="modal:open"><input type = "text" class = "levelClass" readonly = "readonly" placeholder="클릭하여 찾아 주세요"></a><input type ="hidden" class ="levelEquipCode" ></div>
				<div><a href="#classSearch" rel ="modal:open"><input type = "text" class = "levelClass" readonly = "readonly" placeholder="클릭하여 찾아 주세요"></a><input type ="hidden" class ="levelEquipCode" ></div>
			</div>
			<div style="border-bottom : 1px solid black;">실적 입력일</div>
			<div class = "dateInput">
				<div class = "textLayout">시</div><div><input type = "date" id = "inputResultDate"><input type = "time" id = "inputResultTime"></div>
				<div class = "textLayout">처리시간</div><input type = "date" id = "startDate"><input type = "time" id = "startTime"> ~ <input type = "date" id = "endDate" ><input type = "time" id = "endTime"> 
			</div>
			<div style="border-bottom : 1px solid black;">고장코드<span style="float:right;"><button>추가</button></span></div>
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
				<div class = "textLayout">차기장비반영사항</div><div><textarea id = "nextCheckReflection"></textarea><button onclick="checkUp()">확인</button></div>
			</div>
			<div class = "workContent checkUpType">
				설비 타입<br/>
			</div>
		</div>
	</body>
</html>