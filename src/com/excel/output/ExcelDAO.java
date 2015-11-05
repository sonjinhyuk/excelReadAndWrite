package com.excel.output;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
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

public class ExcelDAO {
	private DBmanager bmanager;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	/**
	 * ���������� ó���ϴ� �Լ�
	 * @param fileName
	 * @return �������Ͽ� �����͵�
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
				if( length == 0 ) rows = 1;//�ุ �������� �κ�
				else init = 1;//�����͸� �������� �κ�
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
				                //Ÿ�Ժ��� ���� �б�
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
				int init = 0;
				if( length == 0 ) rows = 1;//�ุ �������� �κ�
				else init = 1;//�����͸� �������� �κ�
				
				for( int r = init ; r < rows; r++ ){
					HSSFRow row = sheet.getRow(r);
					JSONObject json = new JSONObject();
					if( row != null ){
						int cells = row.getPhysicalNumberOfCells();
						for( int c = 0; c < cells; c++ ){
							HSSFCell cell = row.getCell(c);
							String value = "";
							if(cell==null){
				                continue;
				            }else{
				                //Ÿ�Ժ��� ���� �б�
				                switch (cell.getCellType()){
				                case HSSFCell.CELL_TYPE_FORMULA:
				                    value=cell.getCellFormula();
				                    break;
				                case HSSFCell.CELL_TYPE_NUMERIC:
									if (DateUtil.isCellDateFormatted(cell)) {
										objSimpleDateFormat = new SimpleDateFormat("dd-MMM-YY HH:mm:ss", new Locale("en", "us"));
										value = objSimpleDateFormat.format(cell.getDateCellValue())+"";
									} else {
										value=cell.getNumericCellValue()+"";
									}
				                    break;
				                case HSSFCell.CELL_TYPE_STRING:
				                    value=cell.getStringCellValue()+"";
				                    break;
				                case HSSFCell.CELL_TYPE_BLANK:
				                    value=cell.getBooleanCellValue()+"";
				                    break;
				                case HSSFCell.CELL_TYPE_ERROR:
				                    value=cell.getErrorCellValue()+"";
				                    break;
				                }
				            }
							json.put("column" + c, value);
						}
					}
					returndata.add(json);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returndata;
	}
	/**
	 * ù ������ �ε��� db�� �ִ� ���̺� �������� �Լ�
	 * @return db�� �ִ� ���̺��(Json Array)
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
	 * ���õ� ���̺��� �÷� ����Ʈ�� �������� �Լ�
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
	 * db �ִ� �޼ҵ�
	 * @param column ������ �÷�
	 * @param excelData ���� �����͵�
	 * @return ���� �Ǵ� ����
	 */
	public boolean insertDB( String[] column, JSONArray excelData ){
		bmanager = new DBmanager();
		conn = bmanager.getConn();
		int count = 0;
		try {
			StringBuffer sql;
			String sqlString = "INSERT INTO infraData\n\t values(";//sql �պκ� �̸� ����
			
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
					pstmt.setString(j, (String) jobj.get("column"+column[j-1]));
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
	
}
