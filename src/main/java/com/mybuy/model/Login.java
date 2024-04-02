package com.mybuy.model;

public class Login {
    private String usernameOrEmail;
    private String password;
    private String salt;
    private String userType;

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
}
