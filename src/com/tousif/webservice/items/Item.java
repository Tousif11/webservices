package com.tousif.webservice.items;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Item {

	private String itemID;
	private String itemName;
	private String itemPrice;
	
	public String getItemID() {
		return itemID;
	}
	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}
}
