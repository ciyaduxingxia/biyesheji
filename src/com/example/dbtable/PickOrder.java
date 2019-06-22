package com.example.dbtable;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class PickOrder extends LitePalSupport {

	private User user;

	private Item item;
	
	private Picker picker;

	private int id;

	private String PickOrderID;

	private String ManagerID;

	private String ItemID;

	private String PickerID;
	
	private String ItemType;

	private String ItemName;

	private String PcCount;

	private String PcState;

	private java.util.Date PcDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPickOrderID() {
		return PickOrderID;
	}

	public void setPickOrderID(String pickOrderID) {
		PickOrderID = pickOrderID;
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

	public String getPickerID() {
		return PickerID;
	}

	public void setPickerID(String pickerID) {
		PickerID = pickerID;
	}

	public String getItemName() {
		return ItemName;
	}

	public void setItemName(String itemName) {
		ItemName = itemName;
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

	public java.util.Date getPcDate() {
		return PcDate;
	}

	public void setPcDate(java.util.Date pcDate) {
		PcDate = pcDate;
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

	public Picker getPicker() {
		return picker;
	}

	public void setPicker(Picker picker) {
		this.picker = picker;
	}

	public String getItemType() {
		return ItemType;
	}

	public void setItemType(String itemType) {
		ItemType = itemType;
	}
	
	
	
	
}
