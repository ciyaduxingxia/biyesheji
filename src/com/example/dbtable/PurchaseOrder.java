package com.example.dbtable;

import java.util.Date;

import org.litepal.crud.LitePalSupport;

public class PurchaseOrder extends LitePalSupport {

	private Supply supply;

	private User user;

	private Item item;

	private int id;


	private String PcOrderID;

	private String SupplyID;

	private String ManagerID;
	
	private String ItemType;

	private String ItemID;

	private String ItemName;
	
	private String Price;

	private String PcCount;

	private String PcState;

	private java.util.Date PcDate;

	private String Remarks;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPcOrderID() {
		return PcOrderID;
	}

	public void setPcOrderID(String pcOrderID) {
		PcOrderID = pcOrderID;
	}

	public String getSupplyID() {
		return SupplyID;
	}

	public void setSupplyID(String supplyID) {
		SupplyID = supplyID;
	}

	public String getManagerID() {
		return ManagerID;
	}

	public void setManagerID(String managerID) {
		ManagerID = managerID;
	}

	public String getItemID() {
		return ItemID;
	}

	public void setItemID(String itemID) {
		ItemID = itemID;
	}

	public String getPcCount() {
		return PcCount;
	}

	public void setPcCount(String pcCount) {
		PcCount = pcCount;
	}

	public String getPcState() {
		return PcState;
	}

	public void setPcState(String pcState) {
		PcState = pcState;
	}

	public Date getPcDate() {
		return PcDate;
	}

	public void setPcDate(java.util.Date date) {
		PcDate = date;
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

	public String getItemName() {
		return ItemName;
	}

	public void setItemName(String itemName) {
		ItemName = itemName;
	}

	public String getItemType() {
		return ItemType;
	}

	public void setItemType(String itemType) {
		ItemType = itemType;
	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}
	
	

}
