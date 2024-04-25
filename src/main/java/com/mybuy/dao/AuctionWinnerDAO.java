package com.mybuy.dao;

import com.mybuy.model.Auction;
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
        String auctionTable = "Auction";
        String sql = "SELECT * FROM " + auctionTable +" WHERE Auction_Closing_Date < CURDATE() OR (Auction_Closing_Date = CURDATE() AND Auction_Closing_Time < CURTIME())";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

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
}
