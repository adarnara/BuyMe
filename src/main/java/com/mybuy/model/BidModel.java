package com.mybuy.model;

import com.mybuy.dao.BidDAO;
import com.mybuy.dao.IBidDAO;
import java.sql.SQLException;
import java.util.List;

public class BidModel {

    private IBidDAO bidDAO;

    public BidModel() {
        this.bidDAO = new BidDAO();
    }

    public boolean placeBid(Bid newBid) {
        try {
            return bidDAO.placeBid(newBid);
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return false;
    }

    public List<Bid> getBidsByAuctionId(int auctionId) {
        return bidDAO.fetchBidsByAuctionId(auctionId);
    }
}
