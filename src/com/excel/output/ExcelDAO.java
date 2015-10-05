package com.excel.output;

import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ExcelDAO {
	public JSONArray dataAccept(String fileName){
		fileName = fileName.replace("\\", "\\\\");
		JSONArray returndata = new JSONArray();
		try {
			String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			System.out.println(fileName.substring(fileName.lastIndexOf("."), fileName.length()));
			int rows = 0;
			if( extension.equals(".xlsx") ){
				XSSFWorkbook work = new XSSFWorkbook(new FileInputStream(fileName));
				XSSFSheet sheet = work.getSheetAt(0);
				rows = sheet.getPhysicalNumberOfRows();
				for( int r = 0; r < rows; r++ ){
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
				                    value=cell.getNumericCellValue()+"";
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
							json.put("colome" + c, value);
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
}
