package com.mybuy.dao;

import com.mybuy.model.Auction;
import com.mybuy.model.AuctionWinner;
import com.mybuy.model.Bid;
import com.mybuy.utils.ApplicationDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuctionWinnerDAO implements IAuctionWinnerDAO {
    @Override
    public Auction getEndedAuction() {
        Auction endedAuction = null;
        String sql = "SELECT * FROM Auction WHERE (Auction_Closing_Date < CURDATE() OR (Auction_Closing_Date = CURDATE() AND Auction_Closing_Time < CURTIME())) AND auction_status = 'active' ORDER BY Auction_Closing_Date, Auction_Closing_Time ASC LIMIT 1;;";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    endedAuction = new Auction(
                            rs.getInt("Auction_ID"),
                            rs.getDouble("Initial_Price"),
                            rs.getDouble("Current_Price"),
                            rs.getDate("Auction_Closing_Date"),
                            rs.getTime("Auction_Closing_Time"),
                            rs.getDouble("Bid_Increment"),
                            rs.getDouble("Minimum"),
                            rs.getInt("Winner"),
                            rs.getInt("User_Id"),
                            rs.getInt("Item_ID"),
                            rs.getString("auction_status")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting ended auctions: " + e.getMessage());
        }

        return endedAuction;
    }

    @Override
    public Bid getHighestBid(int auctionId) {
        String sql = "SELECT * FROM Bid WHERE Auction_ID = ? ORDER BY Bid_Amount DESC LIMIT 1";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, auctionId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Bid bid = new Bid(
                            rs.getInt("User_Id"),
                            rs.getInt("Auction_ID"),
                            rs.getDouble("Bid_Amount")
                    );

                    return bid;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting ended auctions: " + e.getMessage());
        }

        return null;
    }

    @Override
    public int isUserWinner(int userId) {
        String sql = "SELECT Auction_ID FROM Auction WHERE Winner = ? AND Auction_Closing_Date <= CURDATE() AND (Auction_Closing_Time <= CURTIME() OR Auction_Closing_Date < CURDATE()) AND auction_status = 'completed' LIMIT 1";
        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Auction_ID");
            }
        } catch (SQLException e) {
            System.out.println("Error checking if user is winner: " + e.getMessage());
        }
        return -1;
    }



    @Override
    public boolean updateEndedAuction(AuctionWinner auctionWinner) {
        String sql = "UPDATE Auction SET Winner = ?, auction_status = 'completed' WHERE Auction_ID = ?;";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, auctionWinner.getWinningBid().getUserId());
            pstmt.setInt(2, auctionWinner.getAuction().getAuctionId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating auction: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateEndedAuctionNoWinner(Auction auction) {
        String sql = "UPDATE Auction SET auction_status = 'completed' WHERE Auction_ID = ?;";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, auction.getAuctionId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating auction: " + e.getMessage());
        }
        return false;
    }
}
