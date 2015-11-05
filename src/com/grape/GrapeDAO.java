package com.grape;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import util.DBmanager;
import util.DateUtillity;

public class GrapeDAO {
	private DBmanager bmanager;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
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
	
	
}
