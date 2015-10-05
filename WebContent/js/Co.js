/**
 * 
 */

$(document).ready(function(){
	$("#doh").change(function(){
		var parameter = {
			"dohCode" : $("#doh option:selected").val()
		};
		$.ajax({
			type : "POST",
			url : "/www/Code.do?op=doh",
			data : parameter,
			contenType : "application/son; charset=utf-8",
			dataType : "json",
			beforeSend : function(){
				$("body").css("cursor","wait");
			},
			success : function(data){
				//성공
				var i = 0;
				if( data.length == 0 ){
					$("#city").attr("disabled",true);
				} else {
					$("#city").attr("disabled",false);
					$("#city option").remove();
					$("<option></option>").text("-------------------------").attr("value","none").appendTo("#city");
					for( i = 0; i < data.length; i++ ){
						$("<option></option>").text(data[i].cityname).attr("value",data[i].citycode)
						.appendTo("#city");
					}
				}
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
	
	$("#doh1").change(function(){
		var parameter = {
				"dohCode" : $("#doh1 option:selected").val()
		};
		$.ajax({
			type : "POST",
			url : "/www/Code.do?op=doh",
			data : parameter,
			contenType : "application/son; charset=utf-8",
			dataType : "json",
			beforeSend : function(){
				$("body").css("cursor","wait");
			},
			success : function(data){
				//성공
				var i = 0;
				if( data.length == 0 ){
					$("#city1").attr("disabled",true);
				} else {
					$("#city1").attr("disabled",false);
					$("#city1 option").remove();
					$("<option></option>").text("-------------------------").attr("value","none")
					.appendTo("#city1");
					for( i = 0; i < data.length; i++ ){
						$("<option></option>").text(data[i].cityname).attr("value",data[i].citycode)
						.appendTo("#city1");
					}
				}
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
	
	$("#create").click(function(){
		var parameter = {
				"city": $("#city option:selected").val(),
				"type": $("#business_type option:selected").val(),
				"scale" : $("#business_scale option:selected").val()
			};
		if(parameter.doh == "none" ){
			alert("도를 입력 하여 주십시오.");
			return;
		}
		if( parameter.city == "none" ){
			alert("시,구를 입력 하십시오.");
			return;
		}
			$.ajax({
				type : "POST",
				url : "/www/Code.do?op=check",
				data : parameter,
				contenType : "application/son; charset=utf-8",
				dataType : "json",
				beforeSend : function(){
					$("body").css("cursor","wait");
				},
				success : function( data ){
					var CoCode = fillzero(data.count,3);
					$("#businesscode").val(parameter.city+parameter.type+parameter.scale+CoCode);
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
	$("#saveCode").click(function(){
		var radioCheck = $(":input:radio[name='posible']:checked").val();
		var text = $("#businesscode").val();
		var serialNumber = text.substring(7,10);
		var business_type = $("#business_type option:selected").val();
		var business_scale = $("#business_scale option:selected").val();
		var creater = $("#loginID").text();
		var parameter ={
			"businesscode" : $("#businesscode").val(),	
			"businessName" : $("#businessName").val(),	
			"president" : $("#president").val(),	
			"tel" : $("#tel").val(),	
			"presidentTel" :$("#presidentTel").val(),	
			"fax" :$("#fax").val(),	
			"zipCode" : $("#zipCode").val(),	
			"address" : $("#address").val(),	
			"posible" : radioCheck,
			"serialNumber" : serialNumber,
			"business_type" : business_type,
			"business_scale" : business_scale,
			"creater" : creater,
		};
		
		$.ajax({
			type : "POST",
			url : "/www/Code.do?op=insertCode",
			data : parameter,
			contenType : "application/son; charset=utf-8",
			dataType : "json",
			beforeSend : function(){
				$("body").css("cursor","wait");
			},
			success : function( data ){
				if( data.success == true ){
					alert("성공");
					window.close();
					window.opener.location.reload();
				} else {
					alert("실패");
				}
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
	
	$("#cancel").click(function(){
		window.close();
	});
	
	$("#searchForm").submit(function(){
		var select = $("select[name='searchType'] option:selected").val();
		var parameter = $("<input></input>").attr({
			"name" : "searchType",
			"value" : select
		});
	});
});


function fillzero( obj, len ){
	obj = '00000000000000000000' + obj;
	return obj.substring(obj.length-len);
};
function warning(){
	alert("업체코드 생성은 위의 항목을 이용하여 주십시오.");
}
function zipcodeClick(){
	window.open("zipcode.jsp","우편번호 검색","width=400px,height=500px,toolbar=no,menubar=no,scrollbars=no,resizable=no");
};
