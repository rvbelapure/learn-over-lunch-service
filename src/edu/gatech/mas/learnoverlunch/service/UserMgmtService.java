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

import edu.gatech.mas.learnoverlunch.commons.Constants;
import edu.gatech.mas.learnoverlunch.database.DatabaseHandler;

@Path("/userservice")
public class UserMgmtService {

	@Path("/auth")
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String authenticateUser(String req) {
		String arr[] = req.split("::"); // Client sends username::password
										// string
		String username = arr[0];
		String passwd = null;
		try {
			passwd = arr[1];
		} catch (ArrayIndexOutOfBoundsException e) {
			passwd = null;
		}
		String dbPass = null;
		System.out.println("Login credentials : " + username + " / " + passwd);
		if (username == null || passwd == null)
			return Constants.RESP_NO;
		Connection conn;
		ResultSet rset;
		conn = DatabaseHandler.getConnection();
		try {
			Statement st = (Statement) conn.createStatement();
			rset = st.executeQuery("select passwd from users_mst where uname='"
					+ username + "';");
			if (rset.next())
				dbPass = rset.getString("passwd");
			else
				dbPass = null;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (passwd.equals(dbPass))
			return Constants.RESP_YES;
		else
			return Constants.RESP_NO;
	}

	@Path("/signup")
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String addUser(String req) throws JSONException {
		System.out.println("signup request : " + req);
		JSONObject user = new JSONObject(req);
		String uname = null, fname = null, lname = null, passwd = null, dob = null, email = null, phone = null, edu = null, work = null;
		float rating = (float) 2.5;
		int ratecount = 1;
		try {
			uname = user.getString("uname");
			fname = user.getString("fname");
			lname = user.getString("lname");
			passwd = user.getString("passwd");
//			dob = user.getString("dob");
//			email = user.getString("email");
//			phone = user.getString("phone");
//			edu = user.getString("edu");
//			work = user.getString("work");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Connection conn = DatabaseHandler.getConnection();
		
		try {
			Statement st = (Statement) conn.createStatement();
			ResultSet rset = st.executeQuery("select * from users_mst " +
					"where uname='" + uname +"';");
			if(rset.next())
			{
				try {
				st.close();
				conn.close();
				} catch(SQLException se){}
				return Constants.ERR_UNAME_EXISTS;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Statement st = (Statement) conn.createStatement();
			st.execute("insert into users_mst values ('" + uname + "','"
					+ fname + "','" + lname + "','" + passwd + "','" + dob
					+ "','" + email + "','" + phone + "','" + edu + "','"
					+ work + "'," + rating + ", " + ratecount + ");");
		} catch (SQLException e) {
			e.printStackTrace();
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

	@Path("/profile")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getProfile(String req) throws JSONException {
		JSONObject request, response;
		String myuname, profileuname;
		response = new JSONObject();
		try {
			request = new JSONObject(req);
			myuname = request.getString("myuname");
			profileuname = request.getString("profileuname");
		} catch (JSONException e) {
			response.put("errorcode", Constants.RESP_MALFORMED);
			return response.toString();
		}
		Connection conn;
		ResultSet rset, rs;
		conn = DatabaseHandler.getConnection();

		boolean isFriend = false;
		if (myuname.equals(profileuname)) // if viewing your own profile
			isFriend = true;
		else // check if the users are friends
		{
			try {
				Statement stmt = (Statement) conn.createStatement();
				rs = stmt.executeQuery("select * from friends_mst where "
						+ "(initiator='" + myuname + "' and acceptor='"
						+ profileuname + "') " + "OR (initiator='"
						+ profileuname + "' and acceptor='" + myuname + "');");
				if (rs.next()) {
					if ((rs.getString("status")).equals("yes"))
						isFriend = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		try {
			Statement st = (Statement) conn.createStatement();
			rset = st.executeQuery("select * from users_mst where uname='"
					+ profileuname + "';");
			if (rset.next()) {
				response.put("isFriend", isFriend);
				response.put("errorcode", Constants.RESP_YES);
				response.put("uname", (rset.getString("uname")));
				response.put("fname", (rset.getString("fname")));
				response.put("lname", (rset.getString("lname")));
				response.put("dob", (rset.getString("dob")));
				response.put("email", (rset.getString("email")));
				response.put("phone", (rset.getString("edu")));
				response.put("work", (rset.getString("work")));
				response.put("rating", (rset.getFloat("rating")));
				response.put("ratecount", (rset.getInt("ratecount")));
			} else
				response.put("errorcode", Constants.RESP_NO);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return response.toString();
	}

	@Path("/friend/get")
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllFriends(String uname) {
		JSONArray respArray = new JSONArray();
		JSONObject o;
		Connection conn;
		ResultSet rset;
		conn = DatabaseHandler.getConnection();
		try {
			Statement st = (Statement) conn.createStatement();
			rset = st.executeQuery("select * from users_mst "
					+ "where uname in " + "(select initiator from friends_mst "
					+ "where acceptor='" + uname + "' and status='yes' "
					+ "union " + "select acceptor from friends_mst "
					+ "where initiator='" + uname + "' and status='yes');");
			while (rset.next()) {
				o = new JSONObject();
				o.put("uname", (rset.getString("uname")));
				o.put("fname", (rset.getString("fname")));
				o.put("lname", (rset.getString("lname")));
				o.put("dob", (rset.getString("dob")));
				o.put("email", (rset.getString("email")));
				o.put("phone", (rset.getString("edu")));
				o.put("work", (rset.getString("work")));
				o.put("rating", (rset.getFloat("rating")));
				o.put("ratecount", (rset.getFloat("ratecount")));
				respArray.put(o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return respArray.toString();
	}

	@Path("/friend/add")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addFriend(String req) throws JSONException {
		JSONObject request;
		String myuname, profileuname;
		try {
			request = new JSONObject(req);
			myuname = request.getString("myuname");
			profileuname = request.getString("profileuname");
		} catch (JSONException e) {
			return Constants.RESP_MALFORMED;
		}
		Connection conn;
		ResultSet rs;
		conn = DatabaseHandler.getConnection();

		try {
			Statement stmt = (Statement) conn.createStatement();
			rs = stmt.executeQuery("select * from friends_mst where "
					+ "(initiator='" + myuname + "' and acceptor='"
					+ profileuname + "') " + "OR (initiator='" + profileuname
					+ "' and acceptor='" + myuname + "');");
			if (rs.next()) {
				return Constants.RESP_NO;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		boolean status = false;
		try {
			Statement st = (Statement) conn.createStatement();
			status = st.execute("insert into friends_mst values ('" + myuname
					+ "','" + profileuname + "','pending');");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if(status)
			return Constants.RESP_YES;
		else
			return Constants.RESP_NO;
	}
	
	@Path("/friend/accept")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String acceptFriend(String req) throws JSONException {
		JSONObject request;
		String myuname, profileuname;
		try {
			request = new JSONObject(req);
			myuname = request.getString("myuname");
			profileuname = request.getString("profileuname");
		} catch (JSONException e) {
			return Constants.RESP_MALFORMED;
		}
		Connection conn;
		ResultSet rs;
		boolean status = false;
		conn = DatabaseHandler.getConnection();
		try {
			Statement stmt = (Statement) conn.createStatement();
			rs = stmt.executeQuery("select * from friends_mst where " +
					"OR (initiator='" + profileuname
					+ "' and acceptor='" + myuname + "');");
			if (rs.next()) {
				if((rs.getString("status")).equals("pending"))
				{
					status = stmt.execute("update friends_mst" +
							"set status='yes'" +
							"where (initiator='" + profileuname
					+ "' and acceptor='" + myuname + "');");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(status)
			return Constants.RESP_YES;
		else
			return Constants.RESP_NO;
	}

	@Path("/rateuser")
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String rateUser(String req) {
		String arr[] = req.split("::"); // Client sends username::rating
										// string
		String username = arr[0];
		float rating,dbRating = -1;
		int ratecount = -1;
		try {
			rating = Float.parseFloat(arr[1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			rating = -1;
		}
		System.out.println("User rating : " + username + " / " + rating);
		if (username == null || rating == -1)
			return Constants.RESP_MALFORMED;
		Connection conn;
		ResultSet rset;
		conn = DatabaseHandler.getConnection();
		try {
			Statement st = (Statement) conn.createStatement();
			rset = st.executeQuery("select * from users_mst where uname='"
					+ username + "';");
			if (rset.next())
			{
				dbRating = rset.getFloat("rating");
				ratecount = rset.getInt("ratecount");
			}
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(dbRating == -1 || ratecount == -1)
			return Constants.ERR_FAILURE_GENERIC;
		
		dbRating = ((dbRating * ratecount) + rating) / (float) (ratecount + 1);
		try {
			Statement st = (Statement) conn.createStatement();
			st.execute("update users_mst " +
					   "set rating=" + dbRating + ", ratecount=" + (ratecount + 1) +" " +
					   "where uname='" + username + "';");
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return Constants.ERR_SUCCESS;
	}

}
