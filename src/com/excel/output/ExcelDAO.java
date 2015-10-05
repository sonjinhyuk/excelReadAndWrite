package com.excel.output;

import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelDAO {
	public void data(String fileName){
		System.out.println(fileName);
		fileName = fileName.replace("\\", "\\\\");
		System.out.println(fileName);
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fileName));
			HSSFWorkbook workbook = new HSSFWorkbook(fs);
			HSSFSheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			for( int r = 0; r < rows; r++ ){
				HSSFRow row = sheet.getRow(r);
				if( row != null ){
					int cells = row.getPhysicalNumberOfCells();
					System.out.println(row.getRowNum());
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
