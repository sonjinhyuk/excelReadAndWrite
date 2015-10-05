<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<script type="text/javascript" src="./js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="./js/jquery.form.js"></script>
<script type="text/javascript" src="./js/excel.js"></script>
<title>엑셀</title>
</head>
<body>
	<form method="post" name="frmReview" id="frmReview">
		<input type="text" maxlength="256" id="blog_url" name="blog_url" style="width:400px;" value="http://3mhappyhouse.blog.me/30185320565"/>
		<input type="file" id="excelFile" name="thumb_img" />
		<input type="button" value="작성하기" id = "fileSelect">
	</form>

</body>
</html>