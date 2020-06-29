package com.tousif.webservice.auth;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.sun.jersey.core.util.Base64;
import com.tousif.webservice.util.CreateDBConnection;
import com.tousif.webservice.util.DBConnectionUtill;
import com.tousif.webservice.util.LogClass;

@Path("/userauth")
public class UserAuth {
	
	private StringBuilder sb;

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
	        String query = "SELECT USERNAME,PASSWORD from users";
	        String auth = fetchFromDB(query);
		if(decodedAuth.equals(auth)) {
			return true;
		}else {
			return false;
		}
	}
	
	public String fetchFromDB(String query) {
		
		CreateDBConnection createConnection = new CreateDBConnection();
		LogClass.log.info("Getting DB Connection");
		Connection con = createConnection.getConnection();
		if(con != null) {
			LogClass.log.info("Successfully Created DB Connection");
		}
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<HashMap<String, Object>> dataList = null;
		sb = new StringBuilder();
		
		try {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			dataList = DBConnectionUtill.getResultSetToList(rs);
			if (dataList != null && dataList.size()>0) {
				for(int i = 0; i<dataList.size(); i++) {
					Map<String, Object> userRow = (Map)dataList.get(i);
					if(userRow.get("USERNAME") != null) {
						sb.append((String)userRow.get("USERNAME"));
						sb.append(":");
					}
					if(userRow.get("PASSWORD") != null) {
						sb.append((String)userRow.get("PASSWORD"));
					}
				}
			}
			
		}catch(SQLException e) {
			LogClass.log.info("Caught SQLException "+e);
		}
		finally {
			DBConnectionUtill.closeResultSet(rs);
			DBConnectionUtill.closeStatement(stmt);
			Boolean status = DBConnectionUtill.closeConnection(con);
			if(status) {
				LogClass.log.info("Successfully Closed DB COnnection");
			}else {
				LogClass.log.info("Unable to Closed DB COnnection");
			}
		}
		
		
		return sb.toString();	
	}
}
