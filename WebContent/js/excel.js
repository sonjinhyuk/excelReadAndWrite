/**
 * 
 */

$(document).ready(function(){
	$("#fileSelect").on("change",function(){
		var parameter = {
			"fileName" : "asd"
		};
//		$.ajax({
//			type : "POST",
//			url : "./Excel.do?op=fileInput",
//			data : parameter,
//			contenType : "application/son; charset=utf-8",
//			dataType : "json",
//			beforeSend : function(){
//				$("body").css("cursor","wait");
//			},
//			success : function(data){
//				
//			},
//			error : function(request, status, error) {
//				alert("code:" + request.status + "\n"
//						+ "message:" + request.responseText
//						+ "\n" + "error:" + error);
//			},
//			complete: function(){
//				$("body").css("cursor","default");
//			}
//		});
	});
});

function reviewUploadImg(fileObj) {
	var filePath = fileObj.value;
	var fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
	var fileKind = fileName.split(".")[1];
}
