package com.mybuy.dao;

import com.mybuy.model.Auction;
import com.mybuy.utils.ApplicationDB;
import java.sql.*;
import java.util.Date;

public class AuctionDAO implements IAuctionDAO {

    @Override
    public Auction getAuctionById(int auctionID) {
        Auction auction = null;
        String tableName = "Auction";
        String sql = "SELECT * FROM " + tableName + " WHERE Auction_ID = ?";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, auctionID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    auction = extractAuctionFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching auction by ID: " + e.getMessage());
        }
        return auction;
    }

    // Method to extract auction data from ResultSet
    private Auction extractAuctionFromResultSet(ResultSet rs) throws SQLException {
        int auctionId = rs.getInt("Auction_ID");
        double initialPrice = rs.getDouble("Initial_Price");
        Date closingDate = rs.getDate("Auction_Closing_Date");
        Date closingTime = rs.getTime("Auction_Closing_Time");
        double bidIncrement = rs.getDouble("Bid_Increment");
        int userId = rs.getInt("User_Id");
        int itemId = rs.getInt("Item_ID");

        return new Auction(auctionId, closingDate, closingTime, bidIncrement, initialPrice, userId, itemId);
    }
}
