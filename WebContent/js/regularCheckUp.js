/**
 * 
 */

$(document).ready(function(){
	$(".coGetInfo").click(getCoInfo);
	$("#searchCo").change(function(){
		var parameter = {
			"data" : $("#searchCo option:selected").val()	
		};
		if( parameter.data == "null ") return;
		$.ajax({
			type : "POST",
			url : "./RegularCheck.do?op=searchPlant",
			data : parameter,
			contenType : "application/son; charset=utf-8",
			dataType : "json",
			beforeSend : function(){
				$("body").css("cursor","wait");
			},
			success : function( data ){
				$("#searchPlant option").remove();
				$("#searchPlant").prop("disabled", false);
				var option;
				option = $("<option></option>").attr("value","null");
				option.text("===============");
				$("#searchPlant").append(option);
				for( var i = 0; i < data.length; i++ ){
					option = $("<option></option>");
					option.text(data[i].company);
					$("#searchPlant").append(option);
				}
			},
			error : function(request, status, error) {
				console.log("code:" + request.status + "\n"
						+ "message:" + request.responseText
						+ "\n" + "error:" + error);
			},
			complete: function(){
				$("body").css("cursor","default");
			}
		});
	});
	
	$("#searchPlant").change(function(){
		var parameter = {
			"data" : $("#searchPlant option:selected").val()	
		};
		$.ajax({
			type : "POST",
			url : "./RegularCheck.do?op=searchGenerator",
			data : parameter,
			contenType : "application/son; charset=utf-8",
			dataType : "json",
			beforeSend : function(){
				$("body").css("cursor","wait");
			},
			success : function( data ){
				$("#searchGenerator option").remove();
				$("#searchGenerator").prop("disabled", false);
				var option;
				option = $("<option></option>").attr("value","null");
				option.text("==============");
				$("#searchGenerator").append(option);
				for( var i = 0; i < data.length; i++ ){
					option = $("<option></option>");
					option.text(data[i].equipDese);
					option.attr("value",data[i].equipCode);
					$("#searchGenerator").append(option);
				}
			},
			error : function(request, status, error) {
				console.log("code:" + request.status + "\n"
						+ "message:" + request.responseText
						+ "\n" + "error:" + error);
			},
			complete: function(){
				$("body").css("cursor","default");
			}
		});
	});
	
	$(".levelClass").click(function(){
		var index = $(".levelClass").index(this);
		var generatorCode;
		if( index == 0 ) generatorCode = $("#generatorCode").val();
		else generatorCode = $(".levelEquipCode:eq(" + (index-1) + ")").val();
		var parameter = {
			"data" : index,
			"generatorCode" : generatorCode
		};
		if( parameter.generator == "" ){
			alert("회사 정보 및 발전소 정보를 입력하세요");
			return false;
		}
		$.ajax({
			type : "POST",
			url : "./RegularCheck.do?op=searchClass",
			data : parameter,
			contenType : "application/son; charset=utf-8",
			dataType : "json",
			beforeSend : function(){
				$("body").css("cursor","wait");
			},
			success : function( data ){
				$("#classSearch select option").remove();
				var select = $("#classSearch select");
				var span = $("#classSearch span");
				span.text((index+1)+"레벨의 장비");
				span.attr("value", index);
				var option;
				option = $("<option></option>").attr("value","null");
				option.text("==============");
				select.append(option);
				for( var i = 0; i < data.length; i++ ){
					option = $("<option></option>");
					option.text(data[i].equipDese);
					option.attr("value",data[i].equipCode);
					select.append(option);
				}
			},
			error : function(request, status, error) {
				console.log("code:" + request.status + "\n"
						+ "message:" + request.responseText
						+ "\n" + "error:" + error);
			},
			complete: function(){
				$("body").css("cursor","default");
			}
		});
	});
	
	$(".checkUpType button").click(function(){
		var parameter = {
			"co" : 	$("#co").val(),
			"plant" : $("#plant").val(),
			"generator" : $("#generator").val(),
			"checkName" : $("#checkName").val(),
			"level1" : $(".levelClass:eq(0)").val(),
			"level2" : $(".levelClass:eq(1)").val(),
			"level3" : $(".levelClass:eq(2)").val(),
			"level4" : $(".levelClass:eq(3)").val(),
			"level5" : $(".levelClass:eq(4)").val(),
			"inputResultDate" : $("#inputResultDate").val(),
			"inputResultTime" : $("#inputResultTime").val(),
			"startDate" : $("#startDate").val(),
			"startTime" : $("#startTime").val(),
			"endDate" : $("#endDate").val(),
			"endTime" : $("#endTime").val(),
			"faultLocation" : $("#faultLocation").val(),
			"faultLocationContent" : $("#faultLocationContent").val(),
			"faultContent" : $("#faultContent").val(),
			"faultContentContent" : $("#faultContentContent").val(),
			"faultCause" : $("#faultCause").val(),
			"faultCauseContent" : $("#faultCauseContent").val(),
			"faultPrevention" : $("#faultPrevention").val(),
			"faultPreventionContent" : $("#faultPreventionContent").val(),
			"workContent" : $("#workContent").val(),
			"nextCheckReflection" : $("#nextCheckReflection").val(),
		};
		$.ajax({
			type : "POST",
			url : "./RegularCheck.do?op=saveData",
			data : parameter,
			contenType : "application/son; charset=utf-8",
			dataType : "json",
			beforeSend : function(){
				$("body").css("cursor","wait");
			},
			success : function( data ){
				location.reload(true);
			},
			error : function(request, status, error) {
				console.log("code:" + request.status + "\n"
						+ "message:" + request.responseText
						+ "\n" + "error:" + error);
			},
			complete: function(){
				$("body").css("cursor","default");
			}
		});
	});
});

function getCoInfo(){
	$("#searchPlant option").remove();
	$("#searchPlant").prop("disabled", true);
	$("#searchGenerator option").remove();
	$("#searchGenerator").prop("disabled", true);
	
	$.ajax({
		type : "POST",
		url : "./RegularCheck.do?op=searchCo",
		contenType : "application/son; charset=utf-8",
		dataType : "json",
		beforeSend : function(){
			$("body").css("cursor","wait");
		},
		success : function( data ){
			$("#searchCo option").remove();
			var option;
			option = $("<option></option>").attr("value","null");
			option.text("===============");
			$("#searchCo").append(option);
			for( var i = 0; i < data.length; i++ ){
				option = $("<option></option>");
				option.text(data[i].company);
				$("#searchCo").append(option);
			}
		},
		error : function(request, status, error) {
			console.log("code:" + request.status + "\n"
					+ "message:" + request.responseText
					+ "\n" + "error:" + error);
		},
		complete: function(){
			$("body").css("cursor","default");
		}
	});
}

function insertInput(){
	var Co = $("#searchCo option:selected").text();
	var Plant = $("#searchPlant option:selected").text();
	var Generator = $("#searchGenerator option:selected").text();
	var generatorCode = $("#searchGenerator option:selected").val();
	$("#co").val(Co);
	$("#plant").val(Plant);
	$("#generator").val(Generator);
	$("#generatorCode").val(generatorCode);
}

function select(){
	var index = $("#classSearch span").attr("value");
	var decs  = $("#classSearch select option:selected").text();
	var code  = $("#classSearch select option:selected").val();
	
	$(".levelClass:eq(" + index + ")").val(decs);
	$(".levelEquipCode:eq("+index+")").val(code);
}

function checkUp(){
	var parameter = {
		"data" : $("#workContent").val()
	}
	$.ajax({
		type : "POST",
		url : "./RegularCheck.do?op=checkUp",
		data : parameter,
		contenType : "application/son; charset=utf-8",
		dataType : "json",
		beforeSend : function(){
			$("body").css("cursor","wait");
		},
		success : function( data ){
			for( var i = 0; i < data.length; i++ ){
				var span = $("<span></span>");
				span.text(data[i].type);
				$(".checkUpType").append(span);
			}
		},
		error : function(request, status, error) {
			console.log("code:" + request.status + "\n"
					+ "message:" + request.responseText
					+ "\n" + "error:" + error);
		},
		complete: function(){
			$("body").css("cursor","default");
		}
	});
}