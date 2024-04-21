package com.mybuy.model;

import com.mybuy.dao.BidDAO;
import com.mybuy.dao.IBidDAO;

public class BidModel {

    private IBidDAO bidDAO;

    public BidModel() {
        this.bidDAO = new BidDAO();
    }

    public String placeBid(Bid bid) {
        boolean success = bidDAO.placeBid(bid);
        if (success) {
            return "Bid placed successfully.";
        } else {
            return "Unable to place bid: the bid may be less than the current highest or initial price, or the auction may not be active.";
        }
    }
}
