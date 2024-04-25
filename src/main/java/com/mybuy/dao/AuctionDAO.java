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
        double currentPrice = rs.getDouble("Current_Price");
        Date closingDate = rs.getDate("Auction_Closing_Date");
        Date closingTime = rs.getTime("Auction_Closing_Time");
        double minimum = rs.getDouble("Minimum");
        int winnerId = rs.getInt("Winner");
        int userId = rs.getInt("User_Id");
        int itemId = rs.getInt("Item_ID");
        String status = rs.getString("auction_status");

        return new Auction(auctionId,  initialPrice, currentPrice, closingDate, closingTime, minimum, winnerId, userId, itemId, status);
    }
}
