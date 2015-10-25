package com.regularCheck;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}
