package com.mybuy.model;

public class Delete {
	private String id;
	private UserType type;
	
	public Delete(String id, UserType type) {
		this.setId(id);
		this.setType(type);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}
	
	
}
