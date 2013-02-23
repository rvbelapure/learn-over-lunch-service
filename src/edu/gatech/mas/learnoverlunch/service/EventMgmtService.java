package edu.gatech.mas.learnoverlunch.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mysql.jdbc.Statement;

import edu.gatech.mas.learnoverlunch.commons.Constants;
import edu.gatech.mas.learnoverlunch.database.DatabaseHandler;

@Path("/eventservice")
public class EventMgmtService {

	@Path("/create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String createEvent(String req)
	{
		JSONObject event = null;
		String date, place, topic_name, topic_category, uname;
		int max_members;
		try {
			event = new JSONObject(req);
			date = event.getString("event_date");
			place = event.getString("evnt_place");
			topic_name = event.getString("topic_name");
			topic_category = event.getString("topic_category");
			max_members = event.getInt("max_allowed_members");
			uname = event.getString("event_members");
		} catch (JSONException e) {
			return Constants.RESP_MALFORMED;
		}
		Connection conn = DatabaseHandler.getConnection();
		try {
			Statement st = (Statement) conn.createStatement();
			st.execute("insert into events_mst values " +
					"( null, '"+ date +"', '" + place + "', '" + topic_name + "', '" + topic_category 
					+ "', " + max_members + ");");
			ResultSet rset = st.executeQuery("select MAX(event_id) as maxid from events_mst;");
			int id;
			if(rset.next())
			{
				id = rset.getInt("maxid");
				st.execute("insert into event_attendees values (" + id + ",'" + uname + "');");
			}
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return Constants.RESP_NO;
		} finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return Constants.RESP_YES;
	}
}
