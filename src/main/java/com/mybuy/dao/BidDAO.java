package com.mybuy.dao;

import com.mybuy.model.Bid;
import com.mybuy.utils.ApplicationDB;
import java.sql.*;

public class BidDAO implements IBidDAO {

    @Override
    public boolean placeBid(Bid bid) {
        Connection conn = null;
        PreparedStatement pstmtQuery = null;
        PreparedStatement pstmtInsert = null;
        PreparedStatement pstmtUpdateAuction = null;
        try {
            conn = ApplicationDB.getConnection();
            conn.setAutoCommit(false);

            String sqlQuery = "SELECT a.Currtusent_Price, a.auction_status, MAX(b.Bid_Amount) AS User_Highest_Bid " +
                    "FROM Auction a LEFT JOIN Bid b ON a.Auction_ID = b.Auction_ID AND b.User_Id = ? " +
                    "WHERE a.Auction_ID = ? GROUP BY a.Auction_ID";
            pstmtQuery = conn.prepareStatement(sqlQuery);
            pstmtQuery.setInt(1, bid.getUserId());
            pstmtQuery.setInt(2, bid.getAuctionId());
            ResultSet rs = pstmtQuery.executeQuery();

            double currentHighestBid = 0;
            double userHighestBid = 0;
            String auctionStatus = null;
            if (rs.next()) {
                currentHighestBid = rs.getDouble("Current_Price");
                userHighestBid = rs.getDouble("User_Highest_Bid");
                auctionStatus = rs.getString("auction_status");
            }

            if ("active".equalsIgnoreCase(auctionStatus) &&
                    bid.getBidAmount() > currentHighestBid &&
                    bid.getBidAmount() > userHighestBid) {

                String sqlInsert = "INSERT INTO Bid (Bid_Date, Bid_time, Bid_Amount, Auction_ID, User_Id) VALUES (CURDATE(), CURTIME(), ?, ?, ?)";
                pstmtInsert = conn.prepareStatement(sqlInsert);
                pstmtInsert.setDouble(1, bid.getBidAmount());
                pstmtInsert.setInt(2, bid.getAuctionId());
                pstmtInsert.setInt(3, bid.getUserId());
                pstmtInsert.executeUpdate();

                String sqlUpdateAuction = "UPDATE Auction SET Current_Price = ? WHERE Auction_ID = ?";
                pstmtUpdateAuction = conn.prepareStatement(sqlUpdateAuction);
                pstmtUpdateAuction.setDouble(1, bid.getBidAmount());
                pstmtUpdateAuction.setInt(2, bid.getAuctionId());
                pstmtUpdateAuction.executeUpdate();

                conn.commit();
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("SQL exception during bid placement: " + e.getMessage());
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.out.println("SQL exception on rollback: " + ex.getMessage());
            }
            return false;
        } finally {
            commitToDB(pstmtQuery, pstmtInsert, pstmtUpdateAuction, conn);
        }
    }

    private static void commitToDB(PreparedStatement pstmtQuery, PreparedStatement pstmtInsert, PreparedStatement pstmtUpdateAuction, Connection conn) {
        try {
            if (pstmtQuery != null) pstmtQuery.close();
            if (pstmtInsert != null) pstmtInsert.close();
            if (pstmtUpdateAuction != null) pstmtUpdateAuction.close();
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("SQL exception on closing resources: " + e.getMessage());
        }
    }
}
