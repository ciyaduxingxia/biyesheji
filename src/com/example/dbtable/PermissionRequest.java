package com.example.dbtable;

import java.util.Date;

import org.litepal.crud.LitePalSupport;

public class PermissionRequest extends LitePalSupport{

	private int id;
	
	private String requester;
	
	private Date date;
	
	private String state;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
}
