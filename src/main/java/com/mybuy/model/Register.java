package com.mybuy.model;

public class Register {
    private String username;
    private String email;
    private String password;
    private String salt;
    private UserType type;
    
    public Register(String username, String email, String password, UserType type) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    public UserType getType() {
    	return type;
    }
    
    public void setType(UserType type) {
    	this.type = type;
    }
}
