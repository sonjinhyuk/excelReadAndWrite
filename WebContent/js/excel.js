/**
 * 
 */
var excelDatas = new Array();
var tableColumn;
//객체 선언
/**
 * 엑셀리스트에서 option들을 편하게 하기 위한 클래스
 * column이름과 선택여부를 나타낸다.
 * @param tableColumn
 */
function tableColumnArray(data){
	var columnName = data;
	this.getColumnName = function(){
		return columnName;
	}
	this.setColumnName = function( val ){
		columnName = val;
	}
}



$(document).ready(function(){
	//input 파일이 변경될 때 실행하는 함수
	$("#submitBtn").on("click",function(){
//		if( tableColumn == null || tableColumn == "undefined" || tableColumn === "undefined"
//			|| tableColumn == undefined || tableColumn === undefined ){
//			alert("테이블부터 선택하여 주시기 바랍니다.");
//		} else {
			var data = new FormData();
			if( $('#attachFile')[0].files.length == 0 ){
				alert("파일을 선택하여 주시기 바랍니다.");
				return;
			}
            $.each($('#attachFile')[0].files, function(i, file) {          
                data.append('file-' + i, file);
            });
			$.ajax({
				type : "POST",
				url : "./Excel.do?op=fileInput",
				data : data,
				processData: false,
                contentType: false,
				dataType : "json",
				beforeSend : function(){
					$("body").css("cursor","wait");
				},
				success : function( data ){
					if( data == null ){
						alert("엑셀파일만 선택하여 주시기 바랍니다.");
						return;
					}
					$("#excelTable div").remove();
					var div;//캡션역할
					div = $("<div></div>");
					div.text("선택된 " + $("#attachFile").val().split("\\")[$("#attachFile").val().split("\\").length-1] + "파일의 column 리스트");
					$("#excelTable").append(div);
					var divdbTableCoulmn = $("<div></div>").attr("id","dbTableCoulmn");

					var select;
					var option;
					var ind = 0;
					var column = "column"+ind;
					tableColumn = new Array(data.length);
					while( data[0][column] != undefined ){
						tableColumn[ind] = new tableColumnArray(data[0][column]);
						column = "column" + ++ind;
					}
					
					ind = 0;
					column = "column"+ind;
					while( data[0][column] != undefined ){
						div = $("<div></div>");
						div.attr("class","columnList");
						select = $("<select></select>");
						select.attr("class","exclecSelect");
						settingOption(select);
						div.append(select);
						divdbTableCoulmn.append(div);
						
						column = "column" + ++ind;
						
					}
					$("#excelTable").append(divdbTableCoulmn);
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
//		}
	});
	$.ajax({
		type : "POST",
		url : "./Excel.do?op=init",
		contenType : "application/son; charset=utf-8",
		dataType : "json",
		beforeSend : function(){
			$("body").css("cursor","wait");
		},
		success : function( data ){
			if( data.length != 0 ){
				$("#tableSelect option").remove();
				$("#tableSelect").append($("<option></option>").text("테이블 선택").attr("id","null"));
				$("#tableSelect").append($("<option></option>").text("=========").attr("id","null"));
				for( var i = 0; i < data.length; i++ ){
					$("#tableSelect").append($("<option></option>").text(data[i].tableName));
				}
			} else {
				alert("db 또는 table 생성하세요.");
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
	
	
	//opCode == tableCoulmn
	//테이블을 선택했을 시 columnlist 불러오기
	$("#tableSelect").on("change",function(){
		//null체크는 option에서 테이블 선택과 ======(구분자)선택시 안쓰기 위해서
		if($("#tableSelect option:selected").attr("id") != "null"){
			var parameter = {
					"tableName" : $("#tableSelect option:selected").val()
			};
			$.ajax({
				type : "POST",
				url : "./Excel.do?op=tableCoulmn",
				data: parameter,
				contenType : "application/son; charset=utf-8",
				dataType : "json",
				beforeSend : function(){
					$("body").css("cursor","wait");
				},
				success : function( data ){
					$("#dataBaseTable div").remove();
					var div;
					div = $("<div></div>");
					div.text($("#tableSelect option:selected").val()+"의 column리스트");
					$("#dataBaseTable").append(div);
					
					var tableColumnOption;//테이블 선택
					for( var i = 0; i < data.length; i++ ){
						div = $("<div></div>");
						div.attr("class","columnList");
						div.text(data[i].columnName);
						$("#dataBaseTable").append(div);
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
	});
	
//	$(document).on("change",".exclecSelect",changeOption); //1추후개발에정
});


/**
 * 하나 선택되면 없어지게 하는 방향으로 하고있음.
 * @param select(select option 설정)
 */
function settingOption(select){
	for( var e in tableColumn ){
		option = $("<option></option>");
		option.text(tableColumn[e].getColumnName());
		select.append(option);
	}
}

function insertDBData(){
	var datas = "";
	$("#saveButton").prop("disabled",true);
	if( $(".exclecSelect").length == 0 ) {
		$("#saveButton").prop("disabled",false);
		alert("??");
		return false;
	}
	for( var i = 0; i < $(".exclecSelect").length; i++ )
		datas += $(".exclecSelect:eq("+i+") option:selected").index() + ",";
	var parameter = {
		"columnData" : datas
	};
	$.ajax({
		type : "POST",
		url : "./Excel.do?op=insetDB",
		data: parameter,
		contenType : "application/son; charset=utf-8",
		dataType : "text",
		beforeSend : function(){
			$("body").css("cursor","wait");
		},
		success : function( data ){
			if( data == "true" ){
				alert("성공하였습니다.");
				location.reload();
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

/**
 * 2차원배열 1차원배열로 바꾸기
 * servlet에서 1차원배열밖에 받지 못하기 때문
 * @param parameter
 * @param array
 */
function twoArrayToOneArray( parameter, array ){
	var data;
	for( var i = 0; i < array.length; i++ ){
		data = "";
		var ind = 0;
		var column = "column"+ind;
		while( array[i][column] != undefined ){
			data += array[i][column] + ",";
			column = "column" + ++ind;
		}
		parameter.push(data);
	}
}

