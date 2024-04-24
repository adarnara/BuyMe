package com.mybuy.model;

import com.mybuy.dao.ILoginDAO;
import com.mybuy.dao.LoginDAO;
import com.mybuy.utils.ApplicationDB;
import com.mybuy.utils.HashingUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginModel {

    private ILoginDAO loginDAO;

    public LoginModel() {

        this.loginDAO = new LoginDAO();
    }

    public Login authenticateUserLogin(String usernameOrEmail, String password) {
        Login login = loginDAO.getUserByUsernameOrEmail(usernameOrEmail);
        if (login != null) {
            try {
                boolean passwordMatches = HashingUtility.checkPassword(password, login.getPassword(), login.getSalt());
                if (passwordMatches) {
                    return login;
                }
            }
            catch (Exception e) {
                System.out.println("Passwords weren't same");
                return null;
            }
        }
        return null;
    }

    public String getEndUserType(String username) {
        return loginDAO.getEndUserType(username);
    }

    public List<Auction> getAuctions(String username) {
        return loginDAO.getAuctions(username);
    }

    public int getUserId(String username) {
        return loginDAO.getUserId(username);
    }

    public int addAuction(Auction auction) {
        return loginDAO.addAuction(auction);
    }
}