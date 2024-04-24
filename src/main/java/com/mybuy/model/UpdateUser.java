package com.mybuy.model;

public class UpdateUser {
	private String id;
	private String field;
	private String value;
	private String type;
	private String salt = null;
	
    public String getId() {
        return id;
    }
    
    public String getField() {
    	return field;
    }
    
    public String getValue() {
    	return value;
    }
    
    public String getType() {
    	return type;
    }
    
    public String getSalt() {
    	return salt;
    }
    
    public void setValue(String s) {
    	this.value = s;
    }
    
    public void setSalt(String s) {
    	this.salt = s;
    }
}