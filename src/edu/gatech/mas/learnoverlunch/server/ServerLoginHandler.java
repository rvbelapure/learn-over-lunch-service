package edu.gatech.mas.learnoverlunch.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import edu.gatech.mas.learnoverlunch.database.DatabaseHandler;

public class ServerLoginHandler {
	public boolean authenticateAdmin(String passwd)
	{
		String dbPass = null;
		Connection conn;
		ResultSet rset;
		Statement st = null;
		conn = DatabaseHandler.getConnection();
		try {
			st = (Statement) conn.createStatement();
			rset = st.executeQuery("select passwd from users_mst where uname='admin';");
			if (rset.next())
				dbPass = rset.getString("passwd");
			else
				dbPass = null;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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

		if (passwd.equals(dbPass))
			return true;
		else
			return false;
	}
}
