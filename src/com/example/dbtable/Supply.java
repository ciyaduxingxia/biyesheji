package com.example.dbtable;

import java.util.ArrayList;
import java.util.List;

import org.litepal.crud.LitePalSupport;

public class Supply extends LitePalSupport{
	
	private List<PurchaseOrder> pcorderList = new ArrayList<PurchaseOrder>();
	
	private List<InboundOrder> iborderList = new ArrayList<InboundOrder>();
	
	private int id;

	private String SupplyID;
	
	private String SupplyName;
	
	private String Address;
	
	private String Phone;
	
	private String LinkMan;
	
	private String ItemType;
	
	private String ItemName;
	
	
	//邮政编码
	private String PostalCode;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSupplyID() {
		return SupplyID;
	}

	public void setSupplyID(String supplyID) {
		SupplyID = supplyID;
	}

	public String getSupplyName() {
		return SupplyName;
	}

	public void setSupplyName(String supplyName) {
		SupplyName = supplyName;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getLinkMan() {
		return LinkMan;
	}

	public void setLinkMan(String linkMan) {
		LinkMan = linkMan;
	}

	public String getPostalCode() {
		return PostalCode;
	}

	public void setPostalCode(String postalCode) {
		PostalCode = postalCode;
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
