package com.baku.dropbookmarks.resources;





import com.baku.dropbookmarks.core.User;

import io.dropwizard.auth.Auth;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class HelloController {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getMsg()
	{
		return "Hello DropWizard!!!";
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/secured")
	public String getAuthGrret(@Auth User user)
	{
		return "Hello Secured!!";
	}

}
