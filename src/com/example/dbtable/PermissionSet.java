package com.example.dbtable;

import java.util.Date;

import org.litepal.crud.LitePalSupport;

public class PermissionSet extends LitePalSupport {
	
	private int id;

	private String seter;

	private Date date;

	private String requestid;
	
	private String setResult;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSeter() {
		return seter;
	}

	public void setSeter(String seter) {
		this.seter = seter;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getRequestid() {
		return requestid;
	}

	public void setRequestid(String requestid) {
		this.requestid = requestid;
	}

	public String getSetResult() {
		return setResult;
	}

	public void setSetResult(String setResult) {
		this.setResult = setResult;
	}
	
	
}
