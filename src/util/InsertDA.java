package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertDA {
	public static void main(String[] args) {
		DBmanager dbBmanager = new DBmanager();
		Connection conn = dbBmanager.getConn();
		PreparedStatement pstmt;
		int result;
		File f = new File("date.text");
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO regularCheckUp values");
		String name = "PA_FAN_INLET_FLOW_A";
		String content = "테스트할 내용";
		try {
			BufferedReader in = new BufferedReader(new FileReader("date.text"));
			int i = 0;
			int count = 0;
			for ( i = 0; i < 8737; i++ ) {
				if( i%500 == 0 ){
					sql.append("('" + name + "', '" + in.readLine() + "', '" + content + "')");
					if( i + 500 < 8737 ){
						sql.append(", ");
					}
					count++;
				}else {
					in.readLine();
				}
			}
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql.toString());
			result = pstmt.executeUpdate();
			System.out.println(result);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
