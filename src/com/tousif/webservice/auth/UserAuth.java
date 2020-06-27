package com.tousif.webservice.auth;


import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.sun.jersey.core.util.Base64;
import com.tousif.webservice.util.LogClass;

@Path("/userauth")
public class UserAuth {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response userDetailsNotEntered() {
		LogClass.log.info("Username-Password Not Provided");
		return Response.status(200).entity("userDetailsNotEntered").build();
	}
	
	@GET
	@Path("/user")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUseResponser(@HeaderParam("authorization") String authString) {
		JSONObject json =new JSONObject();
		if(!isValid(authString)) {
			json.put("error", "User Not Authenticated");
		}else {
			json.put("Success", "User Authenticated");
		}
		return Response.status(200).entity(json.toString()).build();
		
	}
	@SuppressWarnings("static-access")
	private boolean isValid(String authString) {
		
		String[] authParts = authString.split("\\s+");
		 byte[] bytes = null;
         bytes = new Base64().decode(authParts[1]);
	        String decodedAuth = new String(bytes);
		if(decodedAuth.equals("tousif:123")) {
			return true;
		}else {
			return false;
		}
	}
}
