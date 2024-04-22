package com.mybuy.dao;

import com.mybuy.model.Auction;
import com.mybuy.model.Login;
import com.mybuy.utils.ApplicationDB;
import java.sql.*;
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
                    String password = rs.getString("password");
                    String salt = rs.getString("salt");
                    String username = rs.getString("username");
                    Login login = new Login(usernameOrEmail, password);
                    login.setSalt(salt);
                    login.setUserType(tableName);
                    login.setUsername(username);
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

        if ("Admin".equals(tableName)) {
            loginColumn = "admin_login";
            usernameColumn = "admin_login";
        }
        else if ("CustomerRep".equals(tableName)) {
            loginColumn = "CustomerRep_login";
            usernameColumn = "CustomerRep_login";
        }
        else if ("EndUser".equals(tableName)) {
            loginColumn = "endUser_login";
            usernameColumn = "endUser_login";
        }
        else {
            throw new IllegalArgumentException("Invalid table name");
        }

        String sqlQueryStmt = "SELECT password, salt, " + usernameColumn + " AS username FROM " + tableName + " WHERE " + loginColumn + " = ? OR email_address = ?";
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
    public List<Auction> getAuctions(String username) {
        String auctionTable = "Auction";
        String endUserTable = "EndUser";
        String sql = "SELECT * FROM " + auctionTable + " AS a " +
                "INNER JOIN " + endUserTable + " AS eu ON a.User_Id = eu.User_Id " +
                "WHERE eu.endUser_login = ?";
        List<Auction> auctions = new ArrayList<>();

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                while(rs.next()) {
                    Auction auction = new Auction(
                            rs.getInt("Auction_ID"),
                            rs.getDate("Auction_Closing_Date"),
                            rs.getTime("Auction_Closing_Time"),
                            rs.getDouble("Bid_Increment"),
                            rs.getDouble("Initial_Price"),
                            rs.getInt("User_Id"),
                            rs.getInt("Item_ID")
                    );
                    auctions.add(auction);
                }
                return auctions;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching list of auctions by username: " + e.getMessage());
        }
        return null;
    }
}