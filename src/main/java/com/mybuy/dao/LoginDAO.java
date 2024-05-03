package com.mybuy.dao;

import com.mybuy.model.Auction;
import com.mybuy.model.Login;
import com.mybuy.utils.ApplicationDB;
import com.mybuy.model.UserType;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LoginDAO implements ILoginDAO {

    @Override
    public Login getUserByUsernameOrEmail(String usernameOrEmail) {
        Login login = null;
        login = getUserFromTable(usernameOrEmail, "Admin");
        if (login == null) {
            login = getUserFromTable(usernameOrEmail, "CustomerRep");
            if (login == null) {
                login = getUserFromTable(usernameOrEmail, "EndUser");
            }
        }
        return login;
    }

    //This is query helper function for getUserByUsernameOrEmail()
    private Login getUserFromTable(String usernameOrEmail, String tableName) {
        String sqlQueryStmt = getTableString(tableName);

        try (Connection conn = ApplicationDB.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sqlQueryStmt)) {

            pstmt.setString(1, usernameOrEmail);
            pstmt.setString(2, usernameOrEmail);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String salt = rs.getString("salt");
                    Login login = new Login(usernameOrEmail, username, password, salt);
                    if (tableName.equals("EndUser")) {
                        String userType = rs.getString("user_type");
                        login.setUserType(UserType.fromString(userType));
                    } else if (tableName.equals("CustomerRep")) {
                    	login.setUserID(rs.getString("CustomerRep_ID"));
                    	login.setUserType(UserType.fromString(tableName));
                    } else {
                    	login.setUserType(UserType.fromString(tableName));
                    }
                    return login;
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Couldn't fetch login data from " + tableName + ": " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    //Simpler checker for table string to get the right login column type, helper for getUserFromTable()
    private static String getTableString(String tableName) {
        String loginColumn;
        String usernameColumn;
        String userTypeColumn;

        if ("Admin".equals(tableName)) {
            loginColumn = "admin_login";
            usernameColumn = "admin_login";
            userTypeColumn = " ";
        }
        else if ("CustomerRep".equals(tableName)) {
            loginColumn = "CustomerRep_login";
            usernameColumn = "CustomerRep_ID, CustomerRep_login";
            userTypeColumn = " ";
        }
        else if ("EndUser".equals(tableName)) {
            loginColumn = "endUser_login";
            usernameColumn = "endUser_login";
            userTypeColumn = ", user_type ";
        }
        else {
            throw new IllegalArgumentException("Invalid table name");
        }
        
        String sqlQueryStmt = "SELECT password, salt, " + usernameColumn + " AS username" + userTypeColumn + "FROM " + tableName + " WHERE " + loginColumn + " = ? OR email_address = ?";
        return sqlQueryStmt;
    }

    @Override
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
            System.out.println("Error fetching end user by username: " + e.getMessage());
        }
        return "";
    }

    @Override
    public int getUserId(String username) {
        String tableName = "EndUser";
        String sql = "SELECT * FROM " + tableName + " WHERE endUser_login = ?";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("User_Id");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user id by username: " + e.getMessage());
        }
        return -1;
    }
}