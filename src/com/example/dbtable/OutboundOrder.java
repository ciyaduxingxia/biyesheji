package com.example.dbtable;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class OutboundOrder extends LitePalSupport {

	private Picker picker;

	private User user;

	private Item item;

	private int id;

	private String ObOrderID;

	private String PickerID;

	private String OperatorID;

	private String ItemID;
	
	private String ItemType;
	
	private String ItemName;

	private String ObCount;

	private java.util.Date ObDate;

	private String Remarks;

	public Picker getPicker() {
		return picker;
	}

	public void setPicker(Picker picker) {
		this.picker = picker;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getObOrderID() {
		return ObOrderID;
	}

	public void setObOrderID(String obOrderID) {
		ObOrderID = obOrderID;
	}

	public String getPickerID() {
		return PickerID;
	}

	public void setPickerID(String pickerID) {
		PickerID = pickerID;
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

	public String getObCount() {
		return ObCount;
	}

	public void setObCount(String obCount) {
		ObCount = obCount;
	}
	
	public java.util.Date getObDate() {
		return ObDate;
	}

	public void setObDate(java.util.Date obDate) {
		ObDate = obDate;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
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
