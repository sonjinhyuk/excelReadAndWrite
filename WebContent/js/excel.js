/**
 * 
 */

$(document).ready(function(){
	$("#fileSelect").on("click",function(){
		event.preventDefault();
		var parameter = {
			"data" : "ASD"
		}
		$.ajax({
			type : "POST",
			url : "./Excel.do?op=fileInput",
			data : parameter,
			contenType : "application/son; charset=utf-8",
//			dataType : "json",
			beforeSend : function(){
				$("body").css("cursor","wait");
			},
			success : function(data){
				
			},
			error : function(request, status, error) {
				alert("code:" + request.status + "\n"
						+ "message:" + request.responseText
						+ "\n" + "error:" + error);
			},
			complete: function(){
				$("body").css("cursor","default");
			}
		});
	});
	
    
//
//    $("form#frmReview").submit(function(event) {
//		// disable the default form submission
//		event.preventDefault();
//
//		var fd = new FormData($(this)[0]);
//
//		$.ajax({
//			url : "./Excel.do?op=fileInput",
//			type : "POST",
//			data : fd,
//			cache : false,
//			contentType : false,
//			processData : false,
//			success : function(data) {
//				alert(data);
//				/* alert(data); if json obj. alert(JSON.stringify(data)); */
//			}
//		});
//
//		return false;
//
//	});
	
});

function reviewUploadImg(fileObj) {
	var filePath = fileObj.value;
	var fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
	var fileKind = fileName.split(".")[1];
}
