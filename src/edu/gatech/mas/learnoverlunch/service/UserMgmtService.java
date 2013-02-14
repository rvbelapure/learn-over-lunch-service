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

@Path("/userservice")
public class UserMgmtService {
	
	@Path("/auth")
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String authenticateUser(String req)
	{
		String arr[] = req.split("::");				// Client sends username::password string
		String username = arr[0];
		String passwd = null;
		try
		{
			passwd = arr[1];
		} catch(ArrayIndexOutOfBoundsException e) {
			passwd = null;
		}
		String dbPass = null;
		System.out.println("Login credentials : " + username + " / " + passwd);
		if(username == null || passwd == null)
			return Constants.RESP_NO;
		Connection conn;
		ResultSet rset;
		conn = DatabaseHandler.getConnection();
		try {
			Statement st = (Statement) conn.createStatement();
			rset = st.executeQuery("select passwd from users_mst where uname='"+username+"';");
			if(rset.next())
				dbPass = rset.getString("passwd");
			else
				dbPass = null;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(passwd.equals(dbPass))
			return Constants.RESP_YES;
		else
			return Constants.RESP_NO;
	}
	
	@Path("/signup")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addUser(JSONObject user)
	{
		String uname = null,fname = null,lname = null,passwd = null,dob = null,email = null,phone = null,edu = null,work = null;
		float rating = 0;
		try {
			uname = user.getString("uname");
			fname = user.getString("fname");
			lname = user.getString("lname");
			passwd = user.getString("passwd");
			dob = user.getString("dob");
			email = user.getString("email");
			phone = user.getString("phone");
			edu = user.getString("edu");
			work = user.getString("work");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		Connection conn = DatabaseHandler.getConnection();
		try {
			Statement st = (Statement) conn.createStatement();
			st.execute("insert into users_mst values ('" + uname + "','" + fname + "','" + lname + "','" + passwd + "','" + 
						dob + "','" + email + "','" + phone + "','" + edu + "','" + work + "'," + rating + ");");
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Constants.RESP_YES;
	}
}
