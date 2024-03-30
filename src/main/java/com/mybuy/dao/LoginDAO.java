package com.mybuy.dao;

import com.mybuy.model.Login;
import com.mybuy.utils.ApplicationDB;

import java.sql.*;

public class LoginDAO implements ILoginDAO {

    @Override
    public Login getUserByUsernameOrEmail(String usernameOrEmail) {
        String sql = "SELECT password, salt FROM EndUser WHERE endUser_login = ? OR email_address = ?";
        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usernameOrEmail);
            pstmt.setString(2, usernameOrEmail);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String password = rs.getString("password");
                    String salt = rs.getString("salt");
                    Login login = new Login(usernameOrEmail, password);
                    login.setSalt(salt);
                    return login;
                }
            }
        } catch (SQLException e) {
            System.out.println("Couldn't fetch login data");
        }
        return null;
    }
}
