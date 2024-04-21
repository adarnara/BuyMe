package com.mybuy.dao;

import com.mybuy.model.Bid;
import com.mybuy.utils.ApplicationDB;
import java.sql.*;

public class BidDAO implements IBidDAO {

    @Override
    public boolean placeBid(Bid bid) {
        String sql = "INSERT INTO Bid (Bid_Date, Bid_time, Bid_Amount, Auction_ID, User_Id) VALUES (CURDATE(), CURTIME(), ?, ?, ?)";
        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, bid.getBidAmount());
            pstmt.setInt(2, bid.getAuctionId());
            pstmt.setInt(3, bid.getUserId());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("SQL exception during bid placement: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBid(int bidId, double newBidAmount) {
        String sql = "UPDATE Bid SET Bid_Amount = ?, Bid_Date = CURDATE(), Bid_time = CURTIME() WHERE Bid_ID = ?";
        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, newBidAmount);
            pstmt.setInt(2, bidId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("SQL exception during bid update: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}