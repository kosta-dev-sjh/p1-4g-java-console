package com.kosta.console_rpg.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBManager {
	private static Properties proFile = new Properties();

	/**
	 * 로드
	 */
	static {
		try {
			//외부 properteis파일 로딩하기
			proFile.load(new FileInputStream("src/com/kosta/console_rpg/resources/db.properties"));
			//proFile.load(new FileInputStream("board.properties"));
			
			Class.forName(proFile.getProperty("driverName"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static Properties getProFile() {
		return proFile;
	}
	
	
	/**
	 * 연결 
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(
				proFile.getProperty("url"),
				proFile.getProperty("userName"),
				proFile.getProperty("userPass"));
	}
	
	
	/**
	 * 닫기 (select 전용)
	 */
	public static void close(Connection con, Statement st, ResultSet rs) {
		try {
			if(rs != null) rs.close();
			if(st != null) st.close();
			if(con != null) con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 닫기 (insert, update, delete = DML 전용)
	 */
	public static void dbClose(Connection con, Statement st) {
		try {
			if(st!=null) st.close();
			if(con!=null) con.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

}