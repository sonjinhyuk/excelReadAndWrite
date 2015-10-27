package com.regularCheck;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import util.DBmanager;

public class RegularCheckDAO {
	private DBmanager bmanager;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public JSONArray getCo(){
		bmanager = new DBmanager();
		conn = bmanager.getConn();
		JSONArray returndata = new JSONArray();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select distinct COMPANY_CODE from codegroup");
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			JSONObject json;
			while(rs.next()){
				json = new JSONObject();
				json.put("company", rs.getString("COMPANY_CODE"));
				
				returndata.add(json);
			}
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
	
	public JSONArray getPlant( String companyCode ){
		bmanager = new DBmanager();
		conn = bmanager.getConn();
		JSONArray returndata = new JSONArray();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select EQUIP_DESC,EQUIP_CODE from codegroup where level_no = 1 and COMPANY_CODE = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, companyCode);
			rs = pstmt.executeQuery();
			JSONObject json;
			while(rs.next()){
				json = new JSONObject();
				json.put("equipDese", rs.getString("EQUIP_DESC"));
				json.put("equipCode", rs.getString("EQUIP_CODE"));
				returndata.add(json);
			}
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
	
	public JSONArray getLevelPlant( String generatorCode, int level ){
		bmanager = new DBmanager();
		conn = bmanager.getConn();
		JSONArray returndata = new JSONArray();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select EQUIP_DESC, EQUIP_CODE from codegroup where level_no = ? and PARENT_CODE = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, level);
			pstmt.setString(2, generatorCode);
			rs = pstmt.executeQuery();
			JSONObject json;
			while(rs.next()){
				json = new JSONObject();
				json.put("equipDese", rs.getString("EQUIP_DESC"));
				json.put("equipCode", rs.getString("EQUIP_CODE"));
				returndata.add(json);
			}
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
	
	public boolean insertDataBase( String co, String plant, String generator, String checkName,	String level1,
								String level2, String level3, String level4, String level5, String inputResultDate,
								String inputResultTime, String startDate, String startTime, String endDate, String endTime,
								String faultLocation, String faultLocationContent, String faultContent, String faultContentContent,
								String faultCause, String faultCauseContent, String faultPrevention, String faultPreventionContent,
								String workContent, String nextCheckReflection, JSONArray priority ){
		
		bmanager = new DBmanager();
		conn = bmanager.getConn();
		JSONArray returndata = new JSONArray();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("insert into 테이블이름");//순서가 위와같은 순서가 아니라면 ( column, column, .... 입력 )
			sql.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");//26개
			
			pstmt = conn.prepareStatement(sql.toString());
			int i = 1;
			pstmt.setString(i++, co);pstmt.setString(i++, co);pstmt.setString(i++, co);pstmt.setString(i++, co);pstmt.setString(i++, co);
			pstmt.setString(i++, co);pstmt.setString(i++, co);pstmt.setString(i++, co);pstmt.setString(i++, co);pstmt.setString(i++, co);
			pstmt.setString(i++, co);pstmt.setString(i++, co);pstmt.setString(i++, co);pstmt.setString(i++, co);pstmt.setString(i++, co);
			pstmt.setString(i++, co);pstmt.setString(i++, co);pstmt.setString(i++, co);pstmt.setString(i++, co);pstmt.setString(i++, co);
			pstmt.setString(i++, co);pstmt.setString(i++, co);pstmt.setString(i++, co);pstmt.setString(i++, co);pstmt.setString(i++, co);
			String priorityString = "";
			
			for( int j = 0; j < priority.size(); j++ ){
				priorityString += priority.get(j)+ ",";
			}
			pstmt.setString(i++, priorityString);
			
			int result = 1;// = pstmt.executeUpdate();
			if( result != 0 ){
				return true;
			}
			//26개인데
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bmanager.allclose(conn, pstmt, rs);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return false;
		
	}
}
