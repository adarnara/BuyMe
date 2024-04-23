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