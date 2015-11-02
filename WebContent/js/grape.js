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
					datas = data;
					draw(parameter.period);
					checkDatas = datas[datas.length-1];
//					var length = checkDats.length > $(".list")
					for( var i = 0; i < checkDatas.length; i++ ){
						$(".list:eq("+(i)+")").text(checkDatas[i].powerPlant + "\n" + checkDatas[i].content + "\n" + checkDatas[i].checkTime);
					}
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
		chart: {
			type: 'spline'
		},
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
            enabled: false
        },
        plotOptions: {
            series: {
                cursor: 'pointer',
                point: {
                    events: {
                        click: function () {
                        	if( checkDatas[i].powerPlant == this.series.userOptions.name && checkDatas[i].checkTime.split(" ")[0] == this.category ){
                    			//모달 생성 또는 보여주는 부분
                    		}
                        }
                    },
                    events: {
                        mouseOver: function () {
                        	for( var i = 0; i < checkDatas.length; i++ ){
                        		if( checkDatas[i].powerPlant == this.series.userOptions.name && checkDatas[i].checkTime.split(" ")[0] == this.category ){
                        			alert("정검일자");
                        		}
        					}
                        }
                    }
                }
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