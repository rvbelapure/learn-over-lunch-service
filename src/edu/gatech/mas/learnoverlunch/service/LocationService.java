package edu.gatech.mas.learnoverlunch.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mysql.jdbc.Statement;

import edu.gatech.mas.learnoverlunch.database.DatabaseHandler;

@Path("/locationservice")
public class LocationService {
	@Path("/get")
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String fetchCategories()
	{
		Connection conn;
		ResultSet rset;
		conn = DatabaseHandler.getConnection();
		JSONArray arr = new JSONArray();
		try {
			Statement stmt = (Statement) conn.createStatement();
			rset = stmt.executeQuery("select distinct event_place from events_mst;");
			while (rset.next()) {
				JSONObject o = new JSONObject();
				o.put("event_place", rset.getString("event_place"));
				arr.put(o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println("Get list of places : " + arr.toString());
		return arr.toString();
	}
}
