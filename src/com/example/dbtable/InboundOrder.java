package com.example.dbtable;


import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class InboundOrder extends LitePalSupport {

	private Supply supply;

	private User user;

	private Item item;

	private int id;

	private String IbOrderID;

	private String SupplyID;

	private String OperatorID;
	
	private String ItemType;
	
	private String ItemName;

	private String ItemID;

	private String IbCount;

	private String Price;

	private java.util.Date IbDate;

	private String Remarks;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIbOrderID() {
		return IbOrderID;
	}

	public void setIbOrderID(String ibOrderID) {
		IbOrderID = ibOrderID;
	}

	public String getSupplyID() {
		return SupplyID;
	}

	public void setSupplyID(String supplyID) {
		SupplyID = supplyID;
	}

	public String getOperatorID() {
		return OperatorID;
	}

	public void setOperatorID(String operatorID) {
		OperatorID = operatorID;
	}

	public String getItemID() {
		return ItemID;
	}

	public void setItemID(String itemID) {
		ItemID = itemID;
	}

	public String getIbCount() {
		return IbCount;
	}

	public void setIbCount(String ibCount) {
		IbCount = ibCount;
	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}

	public java.util.Date getIbDate() {
		return IbDate;
	}

	public void setIbDate(java.util.Date ibDate) {
		IbDate = ibDate;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}

	public Supply getSupply() {
		return supply;
	}

	public void setSupply(Supply supply) {
		this.supply = supply;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getItemType() {
		return ItemType;
	}

	public void setItemType(String itemType) {
		ItemType = itemType;
	}

	public String getItemName() {
		return ItemName;
	}

	public void setItemName(String itemName) {
		ItemName = itemName;
	}
	
	

}
