package com.example.dbtable;

import java.util.ArrayList;
import java.util.List;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * User表
 * @author 肖相杰
 *
 */
public class User extends LitePalSupport{
	
	private List<PurchaseOrder> pcorderList = new ArrayList<PurchaseOrder>();
	
	private List<InboundOrder> iborderList = new ArrayList<InboundOrder>();
	
	private List<PickOrder> pickorderList = new ArrayList<PickOrder>();
	
	private List<OutboundOrder> oborderList = new ArrayList<OutboundOrder>();
	
	//运用注解来为字段添加index标签
	
	//默认为主键且自增长
	private int id;
	
	//UerID是用户编号，是唯一的，且不为空
	@Column(unique = true,nullable = false)
	private String UserID;
	
	private String UserName;
	
	private String UserType;
	
	private String password;

	private String Permission;
	
	private String phone;

	public int getId() {
		return id;
	}

	//所有getter和setter方法
	public void setId(int id) {
		this.id = id;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserType() {
		return UserType;
	}

	public void setUserType(String userType) {
		UserType = userType;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPermission() {
		return Permission;
	}

	public void setPermission(String permission) {
		Permission = permission;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	

}
