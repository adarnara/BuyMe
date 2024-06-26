package com.mybuy.dao;

import com.mybuy.model.AutoBid;
import com.mybuy.utils.ApplicationDB;
import java.sql.*;

public class AutoBidDAO implements IAutoBidDAO {

    @Override
    public boolean placeAutoBid(AutoBid autoBid, double bidAmount) throws SQLException {
        try (Connection conn = ApplicationDB.getConnection()) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO Bid (User_Id, Auction_ID, Bid_Amount, MaxBidAmount, Bid_Date, Bid_Time) "
                    + "VALUES (?, ?, ?, ?, CURDATE(), CURTIME())";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, autoBid.getUserId());
                pstmt.setInt(2, autoBid.getAuctionId());
                pstmt.setDouble(3, bidAmount);
                pstmt.setDouble(4, autoBid.getMaxAutoBidAmount());

                int result = pstmt.executeUpdate();
                if (result > 0) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to place auto bid: " + e.getMessage(), e);
        }
    }


    public double[] fetchAuctionDetails(int auctionId) throws SQLException {
        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT COALESCE(LastBid.Current_Price, Auction.Initial_Price) AS Current_Price, Auction.Bid_Increment, COALESCE(LastBid.User_Id, 0) AS User_Id FROM Auction LEFT JOIN (SELECT User_Id, Auction_ID, Bid_Amount AS Current_Price FROM Bid WHERE Auction_ID = ? ORDER BY Bid_ID DESC LIMIT 1) AS LastBid ON Auction.Auction_ID = LastBid.Auction_ID WHERE Auction.Auction_ID = ? AND Auction.auction_status = 'active'")) {
            pstmt.setInt(1, auctionId);
            pstmt.setInt(2, auctionId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double currentPrice = rs.getDouble("Current_Price");
                double bidIncrement = rs.getDouble("Bid_Increment");
                int lastBidUserId = rs.getInt("User_Id");
                return new double[]{currentPrice, bidIncrement, lastBidUserId};
            } else {
                return null;
            }
        }
    }



    public void updateCurrentPrice(int auctionId, double newPrice) throws SQLException {
        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE Auction SET Current_Price = ? WHERE Auction_ID = ?")) {
            pstmt.setDouble(1, newPrice);
            pstmt.setInt(2, auctionId);
            pstmt.executeUpdate();
        }
    }

    @Override
    public double fetchCurrentPriceIfNotByUser(int auctionId, int userId) throws SQLException {
        double currentPrice = -1;  // Adarsh: set to -1 to lmk no valid bid.
        String sql = "SELECT a.Current_Price FROM Auction a " +
                "JOIN Bid b ON a.Auction_ID = b.Auction_ID " +
                "WHERE a.Auction_ID = ? AND b.User_Id != ? AND b.Bid_Amount = a.Current_Price " +
                "ORDER BY b.Bid_ID DESC LIMIT 1";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, auctionId);
            pstmt.setInt(2, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                currentPrice = rs.getDouble("Current_Price");
            }
        }
        return currentPrice;
    }
}