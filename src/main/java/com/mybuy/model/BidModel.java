package com.mybuy.model;

import com.mybuy.dao.BidDAO;
import com.mybuy.dao.IBidDAO;
import java.sql.SQLException;

public class BidModel {

    private IBidDAO bidDAO;

    public BidModel() {
        this.bidDAO = new BidDAO();
    }

    public String placeBid(Bid newBid) {
        try {
            Bid currentHighestBid = bidDAO.fetchCurrentHighestBid(newBid.getAuctionId());
            double currentBidAmount = currentHighestBid != null ? currentHighestBid.getBidAmount() : 0;

            double userHighestBid = bidDAO.fetchUserHighestBid(newBid.getAuctionId(), newBid.getUserId());
            double auctionBidIncrement = bidDAO.fetchAuctionBidIncrement(newBid.getAuctionId());

            if (newBid.getBidAmount() <= currentBidAmount) {
                return "Bid must be higher than the current highest bid of " + currentBidAmount;
            }

            if (newBid.getBidAmount() <= userHighestBid) {
                return "Bid must be greater than your last highest bid of " + userHighestBid;
            }

            if (newBid.getBidAmount() < currentBidAmount + auctionBidIncrement) {
                return "Bid increment too low; must be at least " + auctionBidIncrement + " more than the current highest bid.";
            }

            if (bidDAO.placeBid(newBid)) {
                return "Bid placed successfully.";
            } else {
                return "Failed to place bid.";
            }
        } catch (SQLException e) {
            return "Database error: " + e.getMessage();
        }
    }
}
