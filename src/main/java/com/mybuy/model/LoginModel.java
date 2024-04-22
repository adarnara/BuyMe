package com.mybuy.model;

import com.mybuy.dao.ILoginDAO;
import com.mybuy.dao.LoginDAO;
import com.mybuy.utils.ApplicationDB;
import com.mybuy.utils.HashingUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        String tableName = "EndUser";
        String sql = "SELECT * FROM " + tableName + " WHERE endUser_login = ?";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("user_type");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching auction by ID: " + e.getMessage());
        }
        return "";
    }
}