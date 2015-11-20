package com.grape;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.poi.util.ArrayUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import ChartDirector.Axis;
import ChartDirector.Chart;
import ChartDirector.LegendBox;
import ChartDirector.LineLayer;
import ChartDirector.TextBox;
import ChartDirector.XYChart;
import util.DBmanager;
import util.DateUtillity;

public class GrapeDAO {
	private DBmanager bmanager;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private int[] color = {0xcc0000, 0x00ff00, 0x0000cc, 0x880088};
	/**
	 * 그래프를 그리기 위한 Data 뽑아내기
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONArray getData( String type, String startDate, String endDate, double calType){
		bmanager = new DBmanager();
		conn = bmanager.getConn();
		JSONArray returndata = new JSONArray();
		JSONArray datasArray;
		JSONArray dataeArray = null;
		try {
			StringBuffer sql;
			String sqlPre;
			ArrayList<String> columnName = getColumnName(type);
			
			int i=1;
			JSONObject json;
			/*
			 * 0번째에 컬럼네임 넣기
			 * 
			 * */
			json = new JSONObject();
			JSONArray headData = new JSONArray();
			json.put("count", columnName.size());
			for( String columnData : columnName ){
				json.put(i++, columnData);
			}
			headData.add(json);
			
			String start = null;
			String end = null;
			DateUtillity du = new DateUtillity();
			//
			//
			//시작날짜 구하기
			//
			if( startDate.equals("today") )	end = du.getToday();//오늘 날짜 구하기.
			else {
				start = startDate;
			}

			if( calType != 0.0 ){
				int month = 0;
				int year = (int) calType;
				if( calType == 0.5 ) month = 6; 
				start = du.calDate(end,year, month, 0 );//특정날짜 일 경우 년 월 일 구하기
			} else 
				end = endDate;
			//끝날짜 구하기
			i = 1;
			for( String column : columnName ){
				sql = new StringBuffer();
				sqlPre = "select sum(convert(real,replace(";
				sqlPre += column;
				sqlPre += ", 'Unit Down', '0'))) as " + column;
				sqlPre += " ,t from (\n select " + column + ", ";
				sql.append(sqlPre);
				sql.append("convert(smalldatetime, SUBSTRING(dateD,8,2) + case SUBSTRING(dateD,4,3) \n");
				sql.append("when 'Jan' then '01' when 'Feb' then '02' when 'Mar' then '03' when 'Apr' then '04' when 'May' then '05' when 'Jun' then '06'\n");
				sql.append("when 'Jul' then '07' when 'Aug' then '08' when 'Sep' then '09' when 'Oct' then '10' when 'Nov' then '11' when 'Dec' then '12'\n");
				sql.append("end + left(dateD,2), 122 ) as t from infraData ) as timeTable\n");
				sql.append("where t between ? and ? group by t");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, start);
				pstmt.setString(2, end);
				rs = pstmt.executeQuery();
				datasArray = new JSONArray();
				dataeArray = new JSONArray();
				while( rs.next() ){
					dataeArray.add(rs.getString(2));
					datasArray.add(Double.parseDouble(rs.getString(column)));
				}
				returndata.add(datasArray);
			}
			headData.add(dataeArray);
			returndata.add(headData);
			
			JSONArray checkDatas = new JSONArray();
			sql = new StringBuffer();
			sql.append("select powerPlant, content,t from (");
			sql.append("\tselect powerPlant, content, convert(smalldatetime, SUBSTRING(dateD,8,2) + case SUBSTRING(dateD,4,3) ");
			sql.append("\twhen 'Jan' then '01' when 'Feb' then '02' when 'Mar' then '03' when 'Apr' then '04' when 'May' then '05' when 'Jun' then '06'");
			sql.append("\twhen 'Jul' then '07' when 'Aug' then '08' when 'Sep' then '09' when 'Oct' then '10' when 'Nov' then '11' when 'Dec' then '12'");
			sql.append("end + left(dateD,2), 122 ) as t from regularCheckUp) as time where t between ? and ? and ( powerplant = ?");
			for( int index = 0; index < columnName.size()-1; index++ ){
				sql.append(" or powerPlant = ? " );
			}
			sql.append(")");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, start);
			pstmt.setString(2, end);
			for( int count = 0; count < columnName.size(); count++ ){
				pstmt.setString(count+3, columnName.get(count));
			}
			rs = pstmt.executeQuery();
			JSONObject checkData;
			while( rs.next() ){
				checkData = new JSONObject();
				checkData.put("powerPlant", rs.getString(1));
				checkData.put("content", rs.getString(2));
				checkData.put("checkTime", rs.getString(3));
				checkDatas.add(checkData);
			}
			
			returndata.add(checkDatas);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				bmanager.allclose(conn, pstmt, rs);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}	
		
		return returndata;
	}
	
	/**
	 * @return 컬럼 이름
	 */
	private ArrayList<String> getColumnName(String type){
		ArrayList<String> returndata = new ArrayList<>();
		bmanager = new DBmanager();
		conn = bmanager.getConn();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select COLUMN_NAME as columnName from information_schema.COLUMNS where table_name = 'infraData'\n");
			sql.append("and COLUMN_NAME like ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, "%"+type+"%");
			rs = pstmt.executeQuery();//query에서 걸러주고
			String types;
			while( rs.next() ){
				types = rs.getString("columnName");
				if( types.contains(type) ){//한번더 걸러준다.
					//원하는 타입의 이름이 들어간다면
					returndata.add(types);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returndata;
	}
	
	/**
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param calType
	 * @return
	 */
	public XYChart getDataCharDirectorChart( String type, String startDate, String endDate, double calType ){
		JSONArray datas = getDataJSON(type, startDate, endDate, calType);
		String json;
		String[] jsonSplit;
		ArrayList<ArrayList<Double>> xData = new ArrayList<>();
		String[] labels = null;
		ArrayList<Double> _1data;
//		{"1":"PAF_A_INLET_VANE_DMPR_POS","2":"PAF_B_INLET_VANE_DMPR_POS","count":2}
		json = datas.get(datas.size()-2).toString();
		int count = Integer.parseInt(json.split("},")[0].split("\"count\":")[1]);
		String[] powerPlant = new String[count];
		for( int i = 0; i < count; i++ ){
			powerPlant[i] = json.split("},")[0].split((i+1)+"\":\"")[1].split("\",")[0];
		}
		for( int i = 0; i < datas.size(); i++ ) {
			json = datas.get(i).toString();
			json = json.replace("[", "");
			json = json.replace("]", "");
			if( i == datas.size()-2 ){//y축 결정과 x축 lables 결정
				labels = json.split("},")[1].split(",");
			} else if( i == datas.size()-1 ) {//checkData
				System.out.println(json);
				String[] checkData = json.split("},");
				String checkDataString;
				for( int cNum = 0; cNum < count; cNum++ ){
					_1data = new ArrayList<>();
					for( int j = 0; j < xData.get(0).size(); j++ ){//날짜별로 체크
						checkDataString = checkData[cNum].split("checkTime\":\"")[1];
						checkDataString = checkDataString.split("\",")[0];
						if( labels[j].replace("\"", "").equals(checkDataString) ){
//							if( )
							_1data.add(Double.valueOf(xData.get(0).get(j)));
						} else {
							_1data.add(Chart.NoValue);
						}
					}
					xData.add(_1data);
				}
			} else {
				jsonSplit = json.split(",");
				_1data = new ArrayList<>();
				for( int j = 0; j < jsonSplit.length; j++ ){
					_1data.add(Double.valueOf(jsonSplit[j]));
				}
				xData.add(_1data);
			}
		}
		XYChart c = new XYChart(800, 400);
		c.setBackground(c.linearGradientColor(0, 0, 0, c.getHeight(), 0xaaccff, 0xffffff), 0x888888);
		c.setRoundedFrame();
		c.setDropShadow();
		TextBox title = c.addTitle("Title", "Arial Bold Italic", 15);//타이틀 제목
		
		title.setMargin2(0, 0, 16, 0);
		c.setPlotArea(150, 80, 400, 230, 0xffffff, -1, -1, c.dashLineColor(0xaaaaaa, Chart.DotLine), -1);
		LegendBox legendBox = c.addLegend(300, 80, false, "Arial Bold", 8);
		legendBox.setAlignment(Chart.BottomCenter);
		legendBox.setBackground(Chart.Transparent, Chart.Transparent);
		
		c.xAxis().setLabels(labels);
		c.xAxis().setLabelStep(200);//x축간의 차이
		c.xAxis().setTitle("day");
		
		c.yAxis().setTitle("Power\n(Watt)").setAlignment(Chart.TopLeft2);
		c.yAxis().setColors(0xcc0000, 0xcc0000, 0xcc0000);
		c.yAxis2().setTitle("Load\n(Mbps)").setAlignment(Chart.TopRight2);
		c.yAxis2().setColors(0x008000, 0x008000, 0x008000);
		
		Axis leftAxis = c.addAxis(Chart.Left, 50);
		leftAxis.setTitle("Temp\n(C)").setAlignment(Chart.TopLeft2);
		leftAxis.setColors(0x0000cc, 0x0000cc, 0x0000cc);
		Axis rightAxis = c.addAxis(Chart.Right, 50);
		rightAxis.setTitle("Error\n(%)").setAlignment(Chart.TopRight2);
		rightAxis.setColors(0x880088, 0x880088, 0x880088);
		
		double[] data;
		LineLayer layer;
		for( int i = 0; i < xData.size(); i++ ){
			if( i > count-1 ){
				data = getData(xData.get(i));
				layer = c.addLineLayer(data);
				layer.addDataSet(data, color[i-count], powerPlant[i-count] + "_Check").setDataSymbol(Chart.GlassSphere2Shape,11);
			}else {
				data = getData(xData.get(i));
				layer = c.addLineLayer(data);
				System.out.println(i);
				layer.addDataSet(data, color[i], powerPlant[i]);
			}
		}
		return c;
	}
	/**
	 * 그래프를 그리기 위한 Data 뽑아내기
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param calType
	 * @return JSONArray형태의 Data들
	 */
	@SuppressWarnings("unchecked")
	private JSONArray getDataJSON( String type, String startDate, String endDate, double calType ){
		bmanager = new DBmanager();
		conn = bmanager.getConn();
		JSONArray returndata = new JSONArray();
		JSONArray datasArray;
		JSONArray dataeArray = null;
		try {
			StringBuffer sql;
			String sqlPre;
			ArrayList<String> columnName = getColumnName(type);
			
			int i=1;
			JSONObject json;
			/*
			 * 0번째에 컬럼네임 넣기
			 * 
			 * */
			json = new JSONObject();
			JSONArray headData = new JSONArray();
			json.put("count", columnName.size());
			for( String columnData : columnName ){
				json.put(i++, columnData);
			}
			headData.add(json);
			
			String start = null;
			String end = null;
			DateUtillity du = new DateUtillity();
			//
			//
			//시작날짜 구하기
			//
			if( startDate.equals("today") )	end = du.getToday();//오늘 날짜 구하기.
			else {
				start = startDate;
			}
			
			if( calType != 0.0 ){
				int month = 0;
				int year = (int) calType;
				if( calType == 0.5 ) month = 6; 
				start = du.calDate(end,year, month, 0 );//특정날짜 일 경우 년 월 일 구하기
			} else 
				end = endDate;
			//끝날짜 구하기
			i = 1;
			for( String column : columnName ){
				sql = new StringBuffer();
				sqlPre = "select sum(convert(real,replace(";
				sqlPre += column;
				sqlPre += ", 'Unit Down', '0'))) as " + column;
				sqlPre += " ,t from (\n select " + column + ", ";
				sql.append(sqlPre);
				sql.append("convert(smalldatetime, SUBSTRING(dateD,8,2) + case SUBSTRING(dateD,4,3) \n");
				sql.append("when 'Jan' then '01' when 'Feb' then '02' when 'Mar' then '03' when 'Apr' then '04' when 'May' then '05' when 'Jun' then '06'\n");
				sql.append("when 'Jul' then '07' when 'Aug' then '08' when 'Sep' then '09' when 'Oct' then '10' when 'Nov' then '11' when 'Dec' then '12'\n");
				sql.append("end + left(dateD,2) + SUBSTRING(dateD,10,12), 120 ) as t from infraData ) as timeTable\n");
				sql.append("where t between ? and ? group by t");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, start);
				pstmt.setString(2, end);
				rs = pstmt.executeQuery();
				datasArray = new JSONArray();
				dataeArray = new JSONArray();
				while( rs.next() ){
					dataeArray.add(rs.getString("t"));
					datasArray.add(Double.parseDouble(rs.getString(column)));
				}
				returndata.add(datasArray);
			}
			headData.add(dataeArray);
			returndata.add(headData);
			
			JSONArray checkDatas = new JSONArray();
			sql = new StringBuffer();
			sql.append("select powerPlant, content,t from (");
			sql.append("\tselect powerPlant, content, convert(smalldatetime, SUBSTRING(dateD,8,2) + case SUBSTRING(dateD,4,3) ");
			sql.append("\twhen 'Jan' then '01' when 'Feb' then '02' when 'Mar' then '03' when 'Apr' then '04' when 'May' then '05' when 'Jun' then '06'");
			sql.append("\twhen 'Jul' then '07' when 'Aug' then '08' when 'Sep' then '09' when 'Oct' then '10' when 'Nov' then '11' when 'Dec' then '12'");
			sql.append("end + left(dateD,2) + SUBSTRING(dateD,10,12), 120 ) as t from regularCheckUp) as time where t between ? and ? and ( powerplant = ?");
			for( int index = 0; index < columnName.size()-1; index++ ){
				sql.append(" or powerPlant = ? " );
			}
			sql.append(")");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, start);
			pstmt.setString(2, end);
			for( int count = 0; count < columnName.size(); count++ ){
				pstmt.setString(count+3, columnName.get(count));
			}
			rs = pstmt.executeQuery();
			JSONObject checkData;
			while( rs.next() ){
				checkData = new JSONObject();
				checkData.put("powerPlant", rs.getString(1));
				checkData.put("content", rs.getString(2));
				checkData.put("checkTime", rs.getString(3));
				checkDatas.add(checkData);
			}
			returndata.add(checkDatas);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bmanager.allclose(conn, pstmt, rs);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}	
		return returndata;
	}
	
	private double[] getData( ArrayList<Double> datas ){
		double[] returndata = new double[datas.size()];
		int i = 0;
		for( Double d : datas ){
			if( d != null ){
				returndata[i++] = d;
			} else {
				returndata[i++] = (Double) null;
			}
		}
		
		return returndata;
		
	}
}
