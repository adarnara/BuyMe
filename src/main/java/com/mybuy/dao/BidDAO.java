package com.mybuy.dao;

import com.mybuy.model.Bid;
import com.mybuy.utils.ApplicationDB;
import java.sql.*;

public class BidDAO implements IBidDAO {

    @Override
    public Bid fetchCurrentHighestBid(int auctionId) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Bid highestBid = null;
        try {
            conn = ApplicationDB.getConnection();
            conn.setAutoCommit(false);
            String sqlQuery = "SELECT MAX(Bid_Amount) AS Highest_Bid FROM Bid WHERE Auction_ID = ?";
            pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setInt(1, auctionId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                double amount = rs.getDouble("Highest_Bid");
                if (!rs.wasNull()) {
                    highestBid = new Bid(-1, auctionId, amount);
                }
            }
            conn.commit();
            return highestBid;
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    @Override
    public double fetchUserHighestBid(int auctionId, int userId) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        double userHighestBid = 0;
        try {
            conn = ApplicationDB.getConnection();
            conn.setAutoCommit(false);
            String sqlQuery = "SELECT MAX(Bid_Amount) AS User_Highest_Bid FROM Bid WHERE Auction_ID = ? AND User_Id = ?";
            pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setInt(1, auctionId);
            pstmt.setInt(2, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                userHighestBid = rs.getDouble("User_Highest_Bid");
            }
            conn.commit();
            return userHighestBid;
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    @Override
    public double fetchAuctionBidIncrement(int auctionId) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        double bidIncrement = 0;
        try {
            conn = ApplicationDB.getConnection();
            conn.setAutoCommit(false);
            String sqlQuery = "SELECT Bid_Increment FROM Auction WHERE Auction_ID = ?";
            pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setInt(1, auctionId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                bidIncrement = rs.getDouble("Bid_Increment");
            }
            conn.commit();
            return bidIncrement;
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    @Override
    public boolean placeBid(Bid bid) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ApplicationDB.getConnection();
            conn.setAutoCommit(false);
            String sqlInsert = "INSERT INTO Bid (User_Id, Auction_ID, Bid_Amount) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sqlInsert);
            pstmt.setInt(1, bid.getUserId());
            pstmt.setInt(2, bid.getAuctionId());
            pstmt.setDouble(3, bid.getBidAmount());
            int result = pstmt.executeUpdate();

            if (result > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}
