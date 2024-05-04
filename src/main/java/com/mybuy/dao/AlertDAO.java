package com.mybuy.dao;

import com.mybuy.model.Alert;
import com.mybuy.utils.ApplicationDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlertDAO implements IAlertDAO {
    @Override
    public List<Alert> checkAndNotify(int userId) {
        List<Alert> alerts = new ArrayList<>();
        String checkUserHighestBid = "SELECT MAX(Bid_Amount) as UserMaxBid FROM Bid WHERE User_Id = ?";
        String checkOtherUsersHigherBids = "SELECT User_Id, Auction_ID, MAX(Bid_Amount) as MaxBid FROM Bid WHERE User_Id != ? AND Bid_Amount > (SELECT MAX(Bid_Amount) FROM Bid WHERE User_Id = ?) GROUP BY User_Id, Auction_ID";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmtUserMax = conn.prepareStatement(checkUserHighestBid)) {
            pstmtUserMax.setInt(1, userId);
            ResultSet rsUserMax = pstmtUserMax.executeQuery();

            if (rsUserMax.next()) {
                double userMaxBid = rsUserMax.getDouble("UserMaxBid");

                try (PreparedStatement pstmtOtherUsers = conn.prepareStatement(checkOtherUsersHigherBids)) {
                    pstmtOtherUsers.setInt(1, userId);
                    pstmtOtherUsers.setInt(2, userId);
                    ResultSet rsOtherUsers = pstmtOtherUsers.executeQuery();

                    while (rsOtherUsers.next()) {
                        int otherUserId = rsOtherUsers.getInt("User_Id");
                        double otherUserMaxBid = rsOtherUsers.getDouble("MaxBid");
                        int auctionId = rsOtherUsers.getInt("Auction_ID");
                        alerts.add(new Alert(otherUserId, otherUserMaxBid, auctionId));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alerts;
    }
}
