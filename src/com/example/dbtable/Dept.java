package com.example.dbtable;

import java.util.ArrayList;
import java.util.List;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class Dept extends LitePalSupport{
	
	private List<Picker> pickerList = new ArrayList<Picker>();

	private int id;

	//编号唯一
	@Column(unique = true,nullable = false) 
	private String DeptID;
	
	private String DeptName;
	
	private String Phone;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeptID() {
		return DeptID;
	}

	public void setDeptID(String deptID) {
		DeptID = deptID;
	}

	public String getDeptName() {
		return DeptName;
	}

	public void setDeptName(String deptName) {
		DeptName = deptName;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public List<Picker> getPickerList() {
		return pickerList;
	}

	public void setPickerList(List<Picker> pickerList) {
		this.pickerList = pickerList;
	}
	
	
}
