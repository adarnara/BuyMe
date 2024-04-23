package com.mybuy.model;

public enum UserType {
	CUSTOMER_REP, END_USER, ADMIN, BUYER, SELLER;
	
    public static UserType fromString(String value) {
        if (value != null) {
            String formattedValue = value.toUpperCase();
            for (UserType userType : UserType.values()) {
                if (userType.name().replace("_","").equals(formattedValue)) {
                    return userType;
                }
            }
        }
        throw new IllegalArgumentException("No enum constant with value: " + value);
    }
    
    public String toName() {
    	String name = this.name();
    	return name.replace("_", "");
    }
}