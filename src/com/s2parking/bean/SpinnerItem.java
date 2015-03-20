package com.s2parking.bean;

public class SpinnerItem {
	
	private int id;
	private String description;	
	
	public SpinnerItem(int id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public int getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
