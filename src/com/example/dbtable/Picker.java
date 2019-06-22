package com.example.dbtable;

import java.util.ArrayList;
import java.util.List;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class Picker extends LitePalSupport {

	private List<PickOrder> pickorderList = new ArrayList<PickOrder>();
	
	private List<OutboundOrder> oborderList = new ArrayList<OutboundOrder>();
	
	private Dept dept;

	private int id;
	
	//编号唯一
	@Column(unique = true,nullable = false) 
	private String PickerID;

	private String PickerName;

	private String Phone;

	private String DeptBelonged;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPickerID() {
		return PickerID;
	}

	public void setPickerID(String pickerID) {
		PickerID = pickerID;
	}

	public String getPickerName() {
		return PickerName;
	}

	public void setPickerName(String pickerName) {
		PickerName = pickerName;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getDeptBelonged() {
		return DeptBelonged;
	}

	public void setDeptBelonged(String deptBelonged) {
		DeptBelonged = deptBelonged;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
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
