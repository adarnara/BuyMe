package com.mybuy.model;

import com.mybuy.dao.AuctionDAO;
import com.mybuy.dao.IAuctionDAO;

import java.util.ArrayList;
import java.util.List;

public class AuctionModel {
    private IAuctionDAO auctionDAO;

    public AuctionModel() {
        this.auctionDAO = new AuctionDAO();
    }

    public List<Auction> getAuctionsByUsername(String username) {
        return auctionDAO.getAuctionsByUsername(username);
    }

    public int addAuction(Auction auction) {
        return auctionDAO.addAuction(auction);
    }

    public Auction getAuctionById(int auctionId) {
        return auctionDAO.getAuctionById(auctionId);
    }
}
