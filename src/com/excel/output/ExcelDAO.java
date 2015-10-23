package com.excel.output;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import util.DBmanager;
import util.DateUtillity;
import util.LoggableStatement;

public class ExcelDAO {
	private DBmanager bmanager;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	/**
	 * 엑셀파일을 처리하는 함수
	 * @param fileName
	 * @return 엑셀파일에 데이터들
	 */
	public JSONArray dataAccept(String fileName, int length ){
		fileName = fileName.replace("\\", "\\\\");
		JSONArray returndata = new JSONArray();
		SimpleDateFormat objSimpleDateFormat;
		try {
			System.out.println(fileName);
			String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			int rows = 0;
			if( extension.equals(".xlsx") ){
				XSSFWorkbook work = new XSSFWorkbook(new FileInputStream(fileName));
				XSSFSheet sheet = work.getSheetAt(0);
				rows = sheet.getPhysicalNumberOfRows();
				int init = 0;
				if( length == 0 ) rows = 1;//행만 가져오는 부분
				else init = 1;//데이터만 가져오는 부분
				for( int r = init ; r < rows; r++ ){
					XSSFRow row = sheet.getRow(r);
					JSONObject json = new JSONObject();
					if( row != null ){
						int cells = row.getPhysicalNumberOfCells();
						for( int c = 0; c < cells; c++ ){
							XSSFCell cell = row.getCell(c);
							String value = "";
							if(cell==null){
				                continue;
				            }else{
				                //타입별로 내용 읽기
				                switch (cell.getCellType()){
				                case XSSFCell.CELL_TYPE_FORMULA:
				                    value=cell.getCellFormula();
				                    break;
				                case XSSFCell.CELL_TYPE_NUMERIC:
									if (DateUtil.isCellDateFormatted(cell)) {
										objSimpleDateFormat = new SimpleDateFormat("dd-MMM-YY HH:mm:ss", new Locale("en", "us"));
										value = objSimpleDateFormat.format(cell.getDateCellValue())+"";
									} else {
										value=cell.getNumericCellValue()+"";
									}
				                    break;
				                case XSSFCell.CELL_TYPE_STRING:
				                    value=cell.getStringCellValue()+"";
				                    break;
				                case XSSFCell.CELL_TYPE_BLANK:
				                    value=cell.getBooleanCellValue()+"";
				                    break;
				                case XSSFCell.CELL_TYPE_ERROR:
				                    value=cell.getErrorCellValue()+"";
				                    break;
				                }
				            }
							json.put("column" + c, value);
						}
					}
					returndata.add(json);
				}
			} else {
				POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fileName));
				HSSFWorkbook workbook = new HSSFWorkbook(fs);
				HSSFSheet sheet = workbook.getSheetAt(0);
				rows = sheet.getPhysicalNumberOfRows();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returndata;
	}
	/**
	 * 첫 페이지 로딩시 db에 있는 테이블 가져오는 함수
	 * @return db에 있는 테이블들(Json Array)
	 */
	public JSONArray init(){
		JSONArray returndata = new JSONArray();
		bmanager = new DBmanager();
		conn = bmanager.getConn();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("Select TABLE_NAME as tablename From INFORMATION_SCHEMA.tables");
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				JSONObject json = new JSONObject();
				json.put("tableName",rs.getString("tablename"));
				returndata.add(json);
			}
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
	 * 선택된 테이블의 컬럼 리스트를 가져오는 함수
	 * @param databaseName 
	 * @return CoulmnList(Json Array)
	 */
	public JSONArray tableCoulmnList( String databaseName ) {
		JSONArray returndata = new JSONArray();
		bmanager = new DBmanager();
		conn = bmanager.getConn();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select COLUMN_NAME as columnName from information_schema.columns where table_name = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, databaseName);
			rs = pstmt.executeQuery();
			while(rs.next()){
				JSONObject json = new JSONObject();
				json.put("columnName",rs.getString("columnName"));
				returndata.add(json);
			}
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
	 * db 넣는 메소드
	 * @param column 선택한 컬럼
	 * @param excelData 엑셀 데이터들
	 * @return 성공 또는 실패
	 */
	public boolean insertDB( String[] column, JSONArray excelData ){
		bmanager = new DBmanager();
		conn = bmanager.getConn();
		int count = 0;
		try {
			StringBuffer sql;
			String sqlString = "INSERT INTO infraData\n\t values(";//sql 앞부분 미리 설정
			
			for( int i = 0; i < column.length; i++ )
				sqlString += "?,";
			sqlString = sqlString.substring(0,sqlString.lastIndexOf(","));
			sqlString += ")";
			JSONObject jobj; 
			for(int i =0; i < excelData.size(); i++ ){
				jobj = (JSONObject) excelData.get(i);
				sql = new StringBuffer();
				sql.append(sqlString);
				pstmt = conn.prepareStatement(sql.toString());
				for( int j = 1; j <= column.length; j++ ){
					pstmt.setString(j, (String) jobj.get("column"+(j-1)));
				}
				pstmt.executeUpdate();
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				bmanager.allclose(conn, pstmt, rs);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}	
		if( count != 0 ) return true;
		return false;
	}
	
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
