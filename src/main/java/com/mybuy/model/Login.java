package com.mybuy.model;

public class Login {
    private String usernameOrEmail;
    private String username;
    private String password;
    private String salt;
    private UserType type;
    
    public Login(String usernameOrEmail, String password) {
    	this.usernameOrEmail = usernameOrEmail;
    	this.password = password;
    }

    
    public Login(String usernameOrEmail, String username, String password, String salt) {
        this.usernameOrEmail = usernameOrEmail;
        this.username = username;
        this.password = password;
        this.salt = salt;
    }

    public Login(String usernameOrEmail, String username, String password, String salt, UserType type) {
        this.usernameOrEmail = usernameOrEmail;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.type = type;
    }

    // Existing getters and setters
    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public UserType getUserType() {
        return type;
    }

    public void setUserType(UserType type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
