package edu.gatech.mas.learnoverlunch.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test/{text}")
public class ServiceTesting {
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHello(@PathParam("text") String txt)
	{
		return "<html><body>Hello !!<br>You reached " + txt + "</body></html>";
	}
}
