package edu.gatech.mas.learnoverlunch.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import edu.gatech.mas.learnoverlunch.commons.PropertyReader;

public class DatabaseHandler {
	static String username = PropertyReader.instance.getProperty("mysql_uname");
	static String password = PropertyReader.instance.getProperty("mysql_passwd");
	static String url = PropertyReader.instance.getProperty("mysql_url");
	static String dbName = PropertyReader.instance.getProperty("mysql_dbname");
	static String driver = PropertyReader.instance.getProperty("mysql_driver");
	
	public static Connection getConnection() {
		username = "learn";
		password = "learnpass!@#";
		url = "jdbc:mysql://127.0.0.1:3306/";
		dbName = "loldb";
		driver = "com.mysql.jdbc.Driver";
		Connection conn = null;
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url+dbName, username, password);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
}
