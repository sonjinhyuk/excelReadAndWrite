<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<script type="text/javascript" src="./js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.21/jquery-ui.min.js"></script>
<script src="http://code.highcharts.com/stock/highstock.js"></script>
<script src="http://code.highcharts.com/stock/modules/exporting.js"></script>
<script type="text/javascript">
createChart = function () {

    $('#container').highcharts('StockChart', {

        rangeSelector: {
            selected: 4
        },

        yAxis: {
        	text: "text"
        },

        plotOptions: {
            series: {
                compare: 'percent'
            }
        },

        tooltip: {
            pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b> ({point.change}%)<br/>',
            valueDecimals: 2
        },

        series: seriesOptions
    });
};

	var seriesOptions = [];
	var parameter = {
		"data" : "PA",
		"period" : 1
	};
	$.getJSON('./Excel.do?op=grape', parameter,    function (data) {
		for( var i = 0; i < data.length-1; i++ ){
			seriesOptions[i] = {
				name : data[data.length-1][0][i+1],
				data : data
			};
		}
		createChart();
	});
</script>
<title>Insert title here</title>
</head>
<body>
	<div id="container" style="height: 400px; min-width: 600px"></div>
</body>
</html>