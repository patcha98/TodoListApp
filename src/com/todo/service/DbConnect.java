package com.todo.service;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnect {
	private static Connection conn = null;
	
	public static void closeConnection() {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Connection getConnection() {
		
		if(conn == null) {
			try {
				//SQLite JDBC üũ
				Class.forName("org.sqlite.JDBC");
				//SQLite �����ͺ��̽� ���Ͽ� ����
				String dbFile = "TodoList.db";
				conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
			

			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return conn;
	}
}
