<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
 function getRealPath(obj){
  obj.select();
 
  document.selection.createRangeCollection()[0].text.toString(); //이게 실행이 안된다면....
//   document.selection.createRangeCollection()[0].text.toString();// 이걸로....
//   document.upimage.real_path.value = document.selection.createRangeCollection()[0].text.toString();
}
</script>
 
<input type="file" id="uploadInput" name="uploadInput" onchange="javascript:getRealPath(this);" />
<input type="hidden" id="real_path" name="real_path" value="" />
</body>
</html>