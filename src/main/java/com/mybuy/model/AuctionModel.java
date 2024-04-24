package com.mybuy.model;

import com.mybuy.dao.AuctionDAO;
import com.mybuy.dao.IAuctionDAO;

public class AuctionModel {
    private IAuctionDAO auctionDAO;

    public AuctionModel() {
        this.auctionDAO = new AuctionDAO();
    }

    public Auction getAuction(int auctionId) {
        return auctionDAO.getAuctionById(auctionId);
    }
}
