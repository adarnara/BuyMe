package com.mybuy.dao;

import com.mybuy.model.Auction;
import com.mybuy.utils.ApplicationDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuctionDAO implements IAuctionDAO {

    @Override
    public Auction getAuctionById(int auctionID) {
        Auction auction = null;
        String sql = "SELECT a.*, eu_winner.endUser_login AS winner_username " +
                "FROM Auction AS a " +
                "LEFT JOIN EndUser AS eu_winner ON a.Winner = eu_winner.User_Id " +
                "WHERE Auction_ID = ?";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, auctionID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    auction = extractAuctionFromResultSet(rs);
                    if (auction.getWinner() != 0) {
                        auction.setWinnerUsername(rs.getString("winner_username"));
                    }
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
        double bidIncrement = rs.getDouble("Bid_Increment");
        double minimum = rs.getDouble("Minimum");
        int winnerId = rs.getInt("Winner");
        int userId = rs.getInt("User_Id");
        int itemId = rs.getInt("Item_ID");
        String status = rs.getString("auction_status");

        return new Auction(auctionId,  initialPrice, currentPrice, closingDate, closingTime, bidIncrement, minimum, winnerId, userId, itemId, status);
    }

    @Override
    public List<Auction> getAuctionsByUsername(String username) {
        String sql = "SELECT a.*, eu_winner.endUser_login AS winner_username " +
                "FROM Auction AS a " +
                "INNER JOIN EndUser AS eu ON a.User_Id = eu.User_Id " +
                "LEFT JOIN EndUser AS eu_winner ON a.Winner = eu_winner.User_Id " +
                "WHERE eu.endUser_login = ?";

        List<Auction> auctions = new ArrayList<>();

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                while(rs.next()) {
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
                    if(auction.getWinner() != 0) {
                        auction.setWinnerUsername(rs.getString("winner_username"));
                    }
                    auctions.add(auction);
                }
                return auctions;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching list of auctions by username: " + e.getMessage());
        }
        return null;
    }

    @Override
    public int addAuction(Auction auction) {
        String auctionTable = "Auction";
        String sql = "INSERT INTO " + auctionTable + " (Current_Price, Auction_Closing_Date, Auction_Closing_time, Bid_Increment, Initial_Price, Minimum, User_Id, Item_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setDouble(1, auction.getInitialPrice());
            pstmt.setDate(2, new java.sql.Date(auction.getAuctionClosingDate().getTime()));
            pstmt.setTime(3, new java.sql.Time(auction.getAuctionClosingTime().getTime()));
            pstmt.setDouble(4, auction.getBidIncrement());
            pstmt.setDouble(5, auction.getInitialPrice());
            pstmt.setDouble(6, auction.getMinimum());
            pstmt.setInt(7, auction.getUserId());
            pstmt.setInt(8, auction.getItemId());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Error adding auction: " + e.getMessage());
        }

        return -1;
    }
}
