package edu.gatech.mas.learnoverlunch.server;

import edu.gatech.mas.learnoverlunch.database.DatabaseHandler;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class AdminServices {

	public void addCategory(String catname) {
		Connection conn = DatabaseHandler.getConnection();
		Statement st = null;
		try {
			st = (Statement) conn.createStatement();
			st.execute("insert into categories_mst values (null,'" + catname + "');");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally
		{
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void addRestaurant(String restaurant, String city) {
		Connection conn = DatabaseHandler.getConnection();
		Statement st = null;
		try {
			st = (Statement) conn.createStatement();
			st.execute("insert into restaurants_mst values ('" + restaurant + "','" +
						city + "', 0, 3);");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally
		{
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
