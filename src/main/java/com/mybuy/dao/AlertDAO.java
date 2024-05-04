package com.mybuy.dao;

import com.mybuy.utils.ApplicationDB;

import java.sql.*;

public class AlertDAO implements IAlertDAO {

    @Override
    public void postAuctionWinnerAlert(int userID, String message, int auctionID) {
        String sql = "INSERT INTO Alerts (User_ID, Message, Alert_Type, Auction_ID) VALUES (?, ?, ?, ?)";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, userID);
            pstmt.setString(2, message);
            pstmt.setString(3, "Auction Winner");
            pstmt.setInt(4, auctionID);

            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Error adding alert: " + e.getMessage());
        }
    }
}
