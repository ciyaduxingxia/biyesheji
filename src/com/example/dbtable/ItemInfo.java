package com.example.dbtable;

public class ItemInfo {
	
	public ItemInfo(){
		
	}
	
	public ItemInfo(String id,String name,String cou,String ccout){
		itemid = id;
		itemname = name;
		count = cou;
		checkcount = ccout;
	}

	private String itemid;
	
	private String itemname;
	
	private String count;
	
	private String checkcount;

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getCheckcount() {
		return checkcount;
	}

	public void setCheckcount(String checkcount) {
		this.checkcount = checkcount;
	}
	
	
}
