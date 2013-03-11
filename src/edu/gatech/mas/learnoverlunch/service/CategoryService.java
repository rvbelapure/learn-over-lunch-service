package edu.gatech.mas.learnoverlunch.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mysql.jdbc.Statement;

import edu.gatech.mas.learnoverlunch.database.DatabaseHandler;

@Path("/categoryservice")
public class CategoryService {
	@Path("/get")
	@Produces(MediaType.TEXT_PLAIN)
	public String fetchCategories()
	{
		Connection conn;
		ResultSet rset;
		conn = DatabaseHandler.getConnection();
		JSONArray arr = new JSONArray();
		try {
			Statement stmt = (Statement) conn.createStatement();
			rset = stmt.executeQuery("select * from categories_mst;");
			while (rset.next()) {
				JSONObject o = new JSONObject();
				o.put("cat_name", rset.getString("cat_name"));
				arr.put(o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return arr.toString();
	}
}
