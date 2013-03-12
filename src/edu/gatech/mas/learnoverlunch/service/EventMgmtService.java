package edu.gatech.mas.learnoverlunch.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mysql.jdbc.Statement;

import edu.gatech.mas.learnoverlunch.commons.Constants;
import edu.gatech.mas.learnoverlunch.database.DatabaseHandler;

@Path("/eventservice")
public class EventMgmtService {

	@SuppressWarnings("deprecation")
	@Path("/create")
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String createEvent(String req)
	{
		JSONObject event = null;
		String place, topic_name, topic_category, uname, topic_desc;
		Timestamp tstp;
		Date date;
		int max_members;
		System.out.println("Create event request : " + req);
		try {
			event = new JSONObject(req);
			date = new Date(event.getString("event_date"));
			place = event.getString("event_place").toLowerCase();
			topic_name = event.getString("topic_name");
			topic_category = event.getString("topic_category");
			topic_desc = event.getString("topic_desc");
			max_members = event.getInt("max_allowed_members");
			uname = event.getString("username");
		} catch (JSONException e) {
			e.printStackTrace();
			return Constants.RESP_MALFORMED;
		}
		tstp = new Timestamp(date.getTime());
		Connection conn = DatabaseHandler.getConnection();
		System.out.println("Create event - date = "+ date + ", timestamp = " + tstp);
		try {
			Statement st = (Statement) conn.createStatement();
			st.execute("insert into events_mst values " +
					"( null, '"+ tstp +"', '" + place + "', '" + topic_name + "', '" + topic_category + "', '" + topic_desc 
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
			return Constants.ERR_FAILURE_GENERIC;
		} finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return Constants.ERR_SUCCESS;
	}
	
	@Path("/get/event/byuser")
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public String getEventByUsername(String uname)
	{
		JSONArray arr = new JSONArray();
		JSONObject o;
		Connection conn = DatabaseHandler.getConnection();
		Statement st = null;
		ResultSet rset;
		System.out.println("Request : Get events for user - " + uname);
		try {
			st = (Statement) conn.createStatement();
			rset = st.executeQuery("select * from events_mst " +
					"where event_id in " +
					"(select event from event_attendees " +
					"where event_members='" + uname + "') " +
					"AND event_date > (CURDATE() - INTERVAL 2 DAY) " +
					"order by event_date ASC;");
			while(rset.next()){
				Timestamp t = rset.getTimestamp("event_date");
				Date d = new Date(t.getTime());
				o = new JSONObject();
				o.put("event_id", rset.getInt("event_id"));
				o.put("event_date",d.toString());
				o.put("event_place", rset.getString("event_place"));
				o.put("topic_name", rset.getString("topic_name"));
				o.put("topic_category",rset.getString("topic_category"));
				o.put("topic_desc", rset.getString("topic_desc"));
				o.put("max_allowed_members", rset.getInt("max_allowed_members"));
				arr.put(o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally
		{
			try {
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Response : " + arr.toString());
		return arr.toString();
	}
	
	@Path("/get/event/bycategory")
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public String getEventByCategory(String category)
	{
		JSONArray arr = new JSONArray();
		JSONObject o;
		Connection conn = DatabaseHandler.getConnection();
		Statement st = null;
		ResultSet rset;
		System.out.println("Request : Get events for category - " + category);
		try {
			st = (Statement) conn.createStatement();
			rset = st.executeQuery("select * from events_mst " +
					"where topic_category='" + category + "' " +
					"AND event_date > CURDATE() " +
					"order by event_date ASC;");
			while(rset.next()){
				Timestamp t = rset.getTimestamp("event_date");
				Date d = new Date(t.getTime());
				o = new JSONObject();
				o.put("event_id", rset.getInt("event_id"));
				o.put("event_date",d.toString());
				o.put("event_place",rset.getString("event_place"));
				o.put("topic_name", rset.getString("topic_name"));
				o.put("topic_category",rset.getString("topic_category"));
				o.put("topic_desc", rset.getString("topic_desc"));
				o.put("max_allowed_members", rset.getInt("max_allowed_members"));
				arr.put(o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally
		{
			try {
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Response : " + arr.toString());
		return arr.toString();
	}
	
	@Path("/get/event/bylocation")
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public String getEventByLocation(String location)
	{
		JSONArray arr = new JSONArray();
		JSONObject o;
		Connection conn = DatabaseHandler.getConnection();
		Statement st = null;
		ResultSet rset;
		System.out.println("Request : Get events for location - " + location);
		try {
			st = (Statement) conn.createStatement();
			rset = st.executeQuery("select * from events_mst " +
					"where event_place='" + location + "' " +
					"AND event_date > CURDATE() " +
					"order by event_date ASC;");
			while(rset.next()){
				Timestamp t = rset.getTimestamp("event_date");
				Date d = new Date(t.getTime());
				o = new JSONObject();
				o.put("event_id", rset.getInt("event_id"));
				o.put("event_date",d.toString());
				o.put("event_place",rset.getString("event_place"));
				o.put("topic_name", rset.getString("topic_name"));
				o.put("topic_category",rset.getString("topic_category"));
				o.put("topic_desc", rset.getString("topic_desc"));
				o.put("max_allowed_members", rset.getInt("max_allowed_members"));
				arr.put(o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally
		{
			try {
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Response : " + arr.toString());
		return arr.toString();
	}

	@Path("/get/members")
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public String getMembersForEvent(String eventid)
	{
		JSONArray arr = new JSONArray();
		int evid = Integer.parseInt(eventid);
		JSONObject o;
		Connection conn = DatabaseHandler.getConnection();
		Statement st = null;
		ResultSet rset;
		
		try {
			st = (Statement) conn.createStatement();
			rset = st.executeQuery("select * from users_mst " +
					"where uname in " +
						"(select event_members from event_attendees " +
						"where event=" + evid + ");");
			while(rset.next()){
				o = new JSONObject();
				o.put("uname", (rset.getString("uname")));
				o.put("fname", (rset.getString("fname")));
				o.put("lname", (rset.getString("lname")));
				o.put("dob", (rset.getString("dob")));
				o.put("email", (rset.getString("email")));
				o.put("phone", (rset.getString("edu")));
				o.put("work", (rset.getString("work")));
				o.put("rating", (rset.getFloat("rating")));
				o.put("ratecount", (rset.getInt("ratecount")));
				arr.put(o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally
		{
			try {
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return arr.toString();
	}
	
	@Path("/isMember")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String isMember(String req)
	{
		String uname = null;
		int eventid = 0;
		try {
			JSONObject o = new JSONObject(req);
			uname = o.getString("uname");
			eventid = o.getInt("event_id");
		} catch (JSONException e) {
			e.printStackTrace();
			return Constants.RESP_MALFORMED;
		}
		
		Connection conn = DatabaseHandler.getConnection();
		Statement st = null;
		ResultSet rset;
		
		try {
			st = (Statement) conn.createStatement();
			rset = st.executeQuery("select * from event_attendees " +
					"where event="+ eventid + " and event_members='" + uname + "';" );
			if(rset.next())
				return Constants.RESP_YES;
			else
				return Constants.RESP_NO;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally
		{
			try {
				st.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return Constants.RESP_NO;
	}
}
