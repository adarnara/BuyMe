package com.mybuy.model;

public class Login {
    private String usernameOrEmail;
    private String username;
    private String password;
    private String salt;
    private String userType;
    private String endUserType;

    public Login(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }

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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEndUserType() {
        return endUserType;
    }

    public void setEndUserType(String endUserType) {
        this.endUserType = endUserType;
    }
}
