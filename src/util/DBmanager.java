package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBmanager {
	private Connection conn;

	public DBmanager() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection("jdbc:sqlserver://202.68.226.162:4938;DatabaseName=donggnag;","donggnag","donggnag");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public Connection getConn(){
		return conn;
	}
	public void closeConn( Connection conn){
		try {
			if( conn != null ) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closePstmt( PreparedStatement pstmt ) {
		try {
			if( pstmt != null ) pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void closeRs( ResultSet rs ) {
		try {
			if( rs != null ) rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void allclose( Connection conn, PreparedStatement pstmt, ResultSet rs ){
		try {
			closeRs(rs);
			closePstmt(pstmt);
			closeConn(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
