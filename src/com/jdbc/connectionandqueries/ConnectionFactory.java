package com.jdbc.connectionandqueries;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/JDBC01";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "";
	
	
	public static Connection getConnection() {
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	
	public static void close(AutoCloseable resource) {
		// AutoCloseable is an interface implemented by both the Connection interface and Statement interface
		// So, rather than creating a close() for the Connection and Statement object separately, we could use a Parent
		// interface i.e AutoCloseable parameter to close() for both Conn and St.
		
		try {
			if (resource != null) {
				resource.close();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
