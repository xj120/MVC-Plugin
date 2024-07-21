package com.plugin.ant.model;

import java.util.List;

public class Smell {
	private SmellType odorType;
	private List<SmellClass> classList;
	private String smellType;

	public Smell(SmellType odorType, List<SmellClass> classList, String smellType) {
		this.odorType=odorType;
		this.classList=classList;
		this.smellType=smellType;
	}
	
	public String getSmellType() {
		return smellType;
	}

	public void setSmellType(String smellType) {
		this.smellType = smellType;
	}
	
	public SmellType getOdorType() {
		return odorType;
	}
	
	public void setOdorType(SmellType odorType) {
		this.odorType = odorType;
	}
	
	public List<SmellClass> getClassList() {
		return classList;
	}
	
	public void setClassList(List<SmellClass> classList) {
		this.classList = classList;
	}
}
