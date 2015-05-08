package com.panfletovia.type;

public enum PublicEnums {
	
	ANDROID("Android");
	
	private String description;
	
	private PublicEnums(String description) {
		this.description = description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}
}// End Class 