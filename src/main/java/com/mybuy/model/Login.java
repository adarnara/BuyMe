package com.mybuy.model;

public class Login {
    private String usernameOrEmail;
    private String password;
    private String salt;

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
    public String getSalt() { return salt; }
    public void setSalt(String salt) {
        this.salt = salt;
    }
}
