package com.mybuy.dao;

import com.mybuy.model.Auction;
import com.mybuy.model.Bid;
import com.mybuy.utils.ApplicationDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuctionWinnerDAO implements IAuctionWinnerDAO {
    @Override
    public List<Auction> getEndedAuctions() {
        List<Auction> endedAuctions = new ArrayList<>();
        String sql = "SELECT * FROM Auction WHERE (Auction_Closing_Date < CURDATE() OR (Auction_Closing_Date = CURDATE() AND Auction_Closing_Time < CURTIME())) AND auction_status = 'active';";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // TODO: double check for situations with no past auctions
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Auction auction = new Auction(
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

                    endedAuctions.add(auction);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting ended auctions: " + e.getMessage());
        }

        return endedAuctions;
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


}
