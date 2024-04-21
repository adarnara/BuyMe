package com.mybuy.model;

import com.mybuy.dao.BidDAO;
import com.mybuy.dao.IBidDAO;

public class BidModel {

    private IBidDAO placeBidDAO;

    public BidModel() {
        this.placeBidDAO = new BidDAO();
    }

    public boolean placeBid(Bid bid) {
        return placeBidDAO.placeBid(bid);
    }

    public boolean updateBid(int bidId, double newBidAmount) {
        return placeBidDAO.updateBid(bidId, newBidAmount);
    }
}
