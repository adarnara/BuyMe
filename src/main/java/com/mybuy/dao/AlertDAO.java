package com.mybuy.dao;

import com.mybuy.model.Alert;
import com.mybuy.model.Auction;
import com.mybuy.utils.ApplicationDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void postAuctionCloseAlert(int userID, String message, Auction auction) {
        String sql = "INSERT INTO Alerts (User_ID, Message, Auction_ID) VALUES (?, ?, ?)";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, userID);
            pstmt.setString(2, message);
            pstmt.setInt(3, auction.getAuctionId());

            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Error adding alert: " + e.getMessage());
        }
    }

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
                            rs.getString("Message")
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

    @Override
    public List<Alert> getBidAlertsForUser(int userId) {
        List<Alert> alerts = new ArrayList<>();
        String sql = "SELECT DISTINCT a.* FROM Alerts a " +
                "JOIN Bid b ON a.Auction_ID = b.Auction_ID " +
                "WHERE b.User_Id != ? AND a.Status = 'Unread' " +
                "AND b.Bid_Amount > (" +
                "SELECT MAX(Bid_Amount) FROM Bid WHERE Auction_ID = b.Auction_ID AND User_Id != ?)";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, userId);  // Pass userId twice to the query

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Alert alert = new Alert(
                        rs.getInt("Alert_ID"),
                        rs.getString("Message"),
                        rs.getString("Status"),
                        rs.getInt("Auction_ID")
                );
                alerts.add(alert);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching bid alerts for user: " + e.getMessage());
        }
        return alerts;
    }
    @Override
    public void postBidAlert(int auctionId, String message, int userIdWhoPlacedBid) {
        String sql = "INSERT INTO Alerts (User_ID, Message, Auction_ID, Status) "
                + "SELECT DISTINCT b.User_Id, ?, b.Auction_ID, 'Unread' "
                + "FROM Bid b "
                + "JOIN ("
                + "    SELECT Auction_ID, MAX(Bid_Amount) as MaxBid "
                + "    FROM Bid "
                + "    WHERE Auction_ID = ? "
                + "    GROUP BY Auction_ID"
                + ") maxb ON b.Auction_ID = maxb.Auction_ID "
                + "WHERE b.Auction_ID = ? "
                + "AND b.Bid_Amount < maxb.MaxBid "
                + "AND b.User_Id != ? "
                + "AND b.User_Id NOT IN ("
                + "    SELECT User_ID FROM Alerts "
                + "    WHERE Auction_ID = ? "
                + "    AND Message = ? "
                + "    AND Status = 'Unread'"
                + ");";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, message);
            pstmt.setInt(2, auctionId);
            pstmt.setInt(3, auctionId);
            pstmt.setInt(4, userIdWhoPlacedBid); // User ID to exclude
            pstmt.setInt(5, auctionId);
            pstmt.setString(6, message);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error posting bid alert: " + e.getMessage());
        }
    }
}
