package com.plugin.ant.model;

import java.util.List;

public class SmellClass {
	private SmellType odorType;
	private String allName;
	private String role;
	
	public SmellClass(SmellType odorType, String allName, String role){
		this.odorType=odorType;
		this.allName=allName;
		this.role=role;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public SmellType getOdorType() {
		return odorType;
	}
	
	public void setOdorType(SmellType odorType) {
		this.odorType = odorType;
	}
	
	public String getAllName() {
		return allName;
	}
	
	public void setAllName(String allName) {
		this.allName = allName;
	}
	
	
}
