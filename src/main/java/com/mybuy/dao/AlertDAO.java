package com.mybuy.dao;

import com.mybuy.model.Alert;
import com.mybuy.model.Auction;
import com.mybuy.utils.ApplicationDB;

import java.sql.*;

public class AlertDAO implements IAlertDAO {
    @Override
    public void postAuctionWinnerAlert(int userID, String message, int auctionID) {
        String sql = "INSERT INTO Alerts (User_ID, Message,Auction_ID) VALUES (?, ?, ?)";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, userID);
            pstmt.setString(2, message);
            pstmt.setInt(3, auctionID);

            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Error adding alert: " + e.getMessage());
        }
    }

//    @Override
//    public void postAuctionCloseAlert(int userID, String message, Auction auction) {
//        String sql = "INSERT INTO Alerts (User_ID, Message, Auction_ID) VALUES (?, ?, ?)";
//
//        try (Connection conn = ApplicationDB.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//
//            pstmt.setInt(1, userID);
//            pstmt.setString(2, message);
//            pstmt.setInt(3, auction.getAuctionId());
//
//            pstmt.executeUpdate();
//        }
//        catch (SQLException e) {
//            System.out.println("Error adding alert: " + e.getMessage());
//        }
//    }

    @Override
    public Alert getNewAlert(int userID) {
        Alert alert = null;
        String sql = "SELECT *\n" +
                "FROM Alerts as a\n" +
                "WHERE a.User_ID = ?\n" +
                "AND a.Status = \"Unread\";";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    alert = new Alert(
                            rs.getInt("Alert_ID"),
                            rs.getString("Message"),
                            rs.getInt("Auction_ID")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching list of auctions by username: " + e.getMessage());
        }

        return alert;
    }

    @Override
    public void closeAlert(Alert alert) {
        String sql = "UPDATE Alerts\n" +
                "SET Status = 'Read'\n" +
                "WHERE Alert_ID = ?;";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, alert.getAlertID());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error closing alert: " + e.getMessage());
        }
    }
}
