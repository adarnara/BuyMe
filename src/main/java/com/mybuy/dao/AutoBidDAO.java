package com.mybuy.dao;

import com.mybuy.model.AutoBid;
import com.mybuy.utils.ApplicationDB;
import java.sql.*;

public class AutoBidDAO implements IAutoBidDAO {

    @Override
    public boolean placeAutoBid(AutoBid autoBid, double bidAmount) throws SQLException {
        try (Connection conn = ApplicationDB.getConnection()) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO Bid (User_Id, Auction_ID, Bid_Amount, Bid_Date, Bid_time) VALUES (?, ?, ?, CURDATE(), CURTIME())";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, autoBid.getUserId());
                pstmt.setInt(2, autoBid.getAuctionId());
                pstmt.setDouble(3, bidAmount);

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
                     "SELECT Current_Price, Bid_Increment FROM Auction WHERE Auction_ID = ? AND auction_status = 'active'")) {
            pstmt.setInt(1, auctionId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double currentPrice = rs.getDouble("Current_Price");
                double bidIncrement = rs.getDouble("Bid_Increment");
                return new double[]{currentPrice, bidIncrement};
            } else {
                return null;
            }
        }
    }
}
