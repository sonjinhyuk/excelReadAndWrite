/**
 * 
 */
var datas;
var checkDatas;
$(document).ready(function(){
	$("#drawGrape").click(function(){
		var startDate = $("#startDate").val(); 
		var endDate = $("#endDate").val();
		var data = $("#_1level").val() != undefined ? $("#_1level").val() : '';
		data += $("#_2level").val() != undefined ? "||" + $("#_2level").val() : ''; 
		data += $("#_3level").val() != undefined ? "||" + $("#_3level").val() : '';
		data += $("#_4level").val() != undefined ? "||" + $("#_4level").val() : '';
		var period = $("#periodDate").val() == "period" ? startDate + " " + endDate : $("#periodDate").val();
		
		var parameter = {
			"data"	: data,
			"period" : period
		};
		
		$.ajax({
			type : "POST",
			url : "./Grape.do?op=grape",
			data: parameter,
			contenType : "application/son; charset=utf-8",
			dataType : "json",
			beforeSend : function(){
				$("body").css("cursor","wait");
			},
			success : function( data ){
				if( data.success == "fail" ) alert("정보를 입력하십시오.");
				else {
					$("#printGrape").attr("src","getchart.jsp?"+data.chart);
					$("#map1").html(data.Image);
//					<%=response.encodeURL("getchart.jsp?" + chart1URL)%>
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
	$("#periodDate").change(function(){
		if( $("#periodDate").val() == "period" ){
			$(".date").prop("disabled",false);
		} else {
			$(".date").prop("disabled",true);
		}
	});
	
});
function draw( period ){
	var series = [];
	var categories = [];
	var count = 1;
	var text = "A(암페어)";//F냐 P냐에 따라 단위가 바뀌어야하낟.
	
	
	for( var i = 0; i < datas.length-1; i++ ){
		if( i == datas.length-2 ){
			for( var j = 0; j < datas[i][1].length; j++ ) categories.push(datas[i][1][j].split(" ")[0]);//시간 넣는 부분
		} else {
			series[i] = {
					name : datas[datas.length-2][0][i+1],
					data: datas[i]
			}
		}
	}
	$("#grapePrint").highcharts({
        title: {
            text: '그래프',
            x: -30 //center
        },
        xAxis: {
            categories: categories
        },
        yAxis: {
            title: {
                text: text
            },
        },
        legend: {
            enabled: true
        },
        chart:{
        	events: {
        		load: function(){
        			plotDelete(this);
        		},
        		redraw: function () {
        			plotDelete(this);
                }
        	}
        },
        plotOptions: {
            series: {
                cursor: 'pointer',
                point: {
                    events: {
                        click: function () {
                        }
                    },
                    events: {
                    	mouseOver: function(){
                    		
                    	}
                    }
                },
            }
        },
        credits: {
        	enabled: true,
        	href: "http://www.highcharts.com",
        	text: "??.com"
        },
        tooltip:{
        	enabled: false
        },
        series: series
    });
}

function drawCharDirector( period ){
	var series = [];
	var categories = [];
	var count = 1;
	var text = "A(암페어)";//F냐 P냐에 따라 단위가 바뀌어야하낟.
}

function plotDelete(obj){
	for( var j = 0; j < obj.series.length; j++ ){
		for( var k = 0; k < obj.series[j].data.length; k++ ){
			var _this = obj.series[j].data[k];
			var check = true;
			for( var i = 0; i < checkDatas.length; i++ ){
				if( checkDatas[i].powerPlant == _this.series.userOptions.name && checkDatas[i].checkTime.split(" ")[0] == _this.category ){
					check = false;
				}
			}
			if( check ) _this.graphic.element.setAttribute("visibility","hidden");
		}
	}
}

function popMsg(obj) {
	var _obj = $(obj);
	var _objClass = _obj.attr("class");
	var _objTime = _obj.attr("value");
	if( _objClass.match("Check") != null ) {
		alert(_objTime);
	}
	
}