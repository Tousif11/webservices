package com.tousif.webservice.auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.tousif.webservice.items.Item;

@Path("/")
public class ItemSearch {
	
	@GET
	@Path("/items")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public List<Item> getAllItems(){
		List<Item> list = new ArrayList<Item>();
		Item item = null;
		for(int i=0; i<2;i++) {
			item = new Item();
			item.setItemID("100"+String.valueOf(i));
			item.setItemName("Test "+String.valueOf(i));
			item.setItemPrice(String.valueOf(i)+".20");
			list.add(item);
		}
		return list;
		
	}
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Item getItemsById(@PathParam("id") int id){
		Item item = new Item();
			if(id == 10) {
			item.setItemID("100");
			item.setItemName("Test ");
			item.setItemPrice("20");
			}	
		return item;
		
	}

	@POST
	@Path("/itemobject")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public String getItemsByObject(InputStream incomingData){
		StringBuilder sb = new StringBuilder();
		JSONObject json = null;
		
		try {
		BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
		String line = null;		
			while((line = in.readLine()) != null) {
				sb.append(line+"\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(sb != null) {
		 json = new JSONObject(sb.toString());
		}
		//String name = null;
		String mobile = null;
		mobile = json.getString("Mobile");
		return sb.toString();
		
	}

}
