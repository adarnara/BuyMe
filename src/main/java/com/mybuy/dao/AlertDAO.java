package com.mybuy.dao;

import com.mybuy.model.Alert;
import com.mybuy.model.Auction;
import com.mybuy.model.Item;
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

    @Override
    public void postExceedAutoBidAlert(int auctionId, double bidAmount, int excludingUserId) {
        String sql = "INSERT INTO Alerts (User_ID, Message, Auction_ID, Status) "
                + "SELECT DISTINCT b.User_Id, "
                + "CONCAT('Your auto-bid limit of ', CAST(b.MaxBidAmount AS CHAR), ' has been exceeded by a new bid of ', CAST(? AS CHAR), ' on auction #', CAST(b.Auction_ID AS CHAR)), "
                + "b.Auction_ID, 'Unread' "
                + "FROM Bid b "
                + "WHERE b.Auction_ID = ? "
                + "AND b.MaxBidAmount IS NOT NULL "
                + "AND b.MaxBidAmount < ? "
                + "AND b.User_Id != ?";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, bidAmount);
            pstmt.setInt(2, auctionId);
            pstmt.setDouble(3, bidAmount);
            pstmt.setInt(4, excludingUserId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Alerts posted successfully for users whose auto-bid limits were exceeded.");
            } else {
                System.out.println("No alerts needed, no auto-bid limits were exceeded.");
            }
        } catch (SQLException e) {
            System.out.println("Error posting auto-bid exceed alert: " + e.getMessage());
        }
    }


    @Override
    public List<Alert> getExceedAutoBidAlertsForUser(int userId) {
        List<Alert> alerts = new ArrayList<>();
        String sql = "SELECT Alert_ID, User_ID, Message, Auction_ID, Status "
                + "FROM Alerts "
                + "WHERE User_ID = ? AND Status = 'Unread' AND Message LIKE 'Your auto-bid limit%';";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
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
            System.out.println("Error fetching exceed auto-bid alerts for user: " + e.getMessage());
        }
        return alerts;
    }

    @Override
    public void updateAlertsForNewAuction(Auction auction) {
        try (Connection conn = ApplicationDB.getConnection()) {
            String sql = "UPDATE Alerts SET Message = ?, Status = 'Unread' " +
                    "WHERE Alert_ID IN (" +
                    "SELECT Alert_ID FROM (" +
                    "SELECT a.Alert_ID FROM Alerts a " +
                    "WHERE a.Status = 'Pending' AND (" +
                    "(a.Item_Name IS NOT NULL AND LEVENSHTEIN_RATIO(a.Item_Name, ?) >= 20) OR " +
                    "(a.Item_Brand IS NOT NULL AND LEVENSHTEIN_RATIO(a.Item_Brand, ?) >= 20) OR " +
                    "(a.Category_Name IS NOT NULL AND LEVENSHTEIN_RATIO(a.Category_Name, ?) >= 20) OR " +
                    "(a.Color IS NOT NULL AND LEVENSHTEIN_RATIO(a.Color, ?) >= 20)" +
                    ")) as subquery)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                Item item = auction.getItem(); // Ensure item is properly initialized and not null
                String categoryName = getCategoryNameById(item.getCategoryId(), conn);
                String message = constructAlertMessage(item, categoryName);

                // Setting parameters for the prepared statement
                pstmt.setString(1, message);
                pstmt.setString(2, item.getName());
                pstmt.setString(3, item.getBrand());
                pstmt.setString(4, categoryName);
                pstmt.setString(5, item.getColor());

                int updatedRows = pstmt.executeUpdate();
                System.out.println("Updated " + updatedRows + " alerts.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating alerts for new auction: " + e.getMessage());
        }
    }

    private String constructAlertMessage(Item item, String categoryName) {
        StringBuilder message = new StringBuilder("The requested ");
        boolean previousAttribute = false;

        if (item.getName() != null && !item.getName().isEmpty()) {
            message.append("item '").append(item.getName()).append("'");
            previousAttribute = true;
        }
        if (item.getBrand() != null && !item.getBrand().isEmpty()) {
            if (previousAttribute) message.append(" and");
            message.append(" brand '").append(item.getBrand()).append("'");
            previousAttribute = true;
        }
        if (categoryName != null && !categoryName.equals("N/A")) {
            if (previousAttribute) message.append(" and");
            message.append(" category '").append(categoryName).append("'");
            previousAttribute = true;
        }
        if (item.getColor() != null && !item.getColor().isEmpty()) {
            if (previousAttribute) message.append(" and");
            message.append(" color '").append(item.getColor()).append("'");
        }
        message.append(" has been posted!");
        return message.toString();
    }

    private String getCategoryNameById(int categoryId, Connection conn) throws SQLException {
        String sql = "SELECT Category_Name FROM Category WHERE Category_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, categoryId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Category_Name");
                }
            }
        }
        return "N/A";
    }



}
