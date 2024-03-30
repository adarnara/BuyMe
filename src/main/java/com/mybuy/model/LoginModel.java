package com.mybuy.model;

import com.mybuy.dao.ILoginDAO;
import com.mybuy.dao.LoginDAO;
import com.mybuy.utils.HashingUtility;

public class LoginModel {

    private ILoginDAO loginDAO;

    public LoginModel() {
        this.loginDAO = new LoginDAO();
    }

    public boolean authenticateUser(String usernameOrEmail, String password) {
        Login login = loginDAO.getUserByUsernameOrEmail(usernameOrEmail);
        if (login != null) {
            try {
                return HashingUtility.checkPassword(password, login.getPassword(), login.getSalt());
            } catch (Exception e) {
                System.out.println("Passwords weren't same");
                return false;
            }
        }
        return false;
    }
}
