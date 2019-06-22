package com.example.dbtable;

import java.util.ArrayList;
import java.util.List;

import org.litepal.crud.LitePalSupport;

public class Item extends LitePalSupport{
	
	private List<PurchaseOrder> pcorderList = new ArrayList<PurchaseOrder>();
	
	private List<InboundOrder> iborderList = new ArrayList<InboundOrder>();
	
	private List<PickOrder> pickorderList = new ArrayList<PickOrder>();
	
	private List<OutboundOrder> oborderList = new ArrayList<OutboundOrder>();
	
	private int id;
	
	private String ItemID;
	
	private String ItemName;
	
	private String ItemType;
	
	private String Count;
	
	private String Unit;
	
	private String Location;
	
	private String Price;
	
	private String UpperLimit;
	
	private String LowerLimit;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemID() {
		return ItemID;
	}

	public void setItemID(String itemID) {
		ItemID = itemID;
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

	public String getCount() {
		return Count;
	}

	public void setCount(String count) {
		Count = count;
	}

	public String getUnit() {
		return Unit;
	}

	public void setUnit(String unit) {
		Unit = unit;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}

	public String getUpperLimit() {
		return UpperLimit;
	}

	public void setUpperLimit(String upperLimit) {
		UpperLimit = upperLimit;
	}

	public String getLowerLimit() {
		return LowerLimit;
	}

	public void setLowerLimit(String lowerLimit) {
		LowerLimit = lowerLimit;
	}

	public List<PurchaseOrder> getPcorderList() {
		return pcorderList;
	}

	public void setPcorderList(List<PurchaseOrder> pcorderList) {
		this.pcorderList = pcorderList;
	}

	public List<InboundOrder> getIborderList() {
		return iborderList;
	}

	public void setIborderList(List<InboundOrder> iborderList) {
		this.iborderList = iborderList;
	}

	public List<PickOrder> getPickorderList() {
		return pickorderList;
	}

	public void setPickorderList(List<PickOrder> pickorderList) {
		this.pickorderList = pickorderList;
	}

	public List<OutboundOrder> getOborderList() {
		return oborderList;
	}

	public void setOborderList(List<OutboundOrder> oborderList) {
		this.oborderList = oborderList;
	}
	
	
	
}
