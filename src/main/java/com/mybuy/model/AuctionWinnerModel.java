package com.mybuy.model;

import com.mybuy.dao.AuctionWinnerDAO;

import java.util.List;

public class AuctionWinnerModel {
    private AuctionWinnerDAO auctionWinnerDAO;

    public AuctionWinnerModel() {
        auctionWinnerDAO = new AuctionWinnerDAO();
    }

    public void getEndedAuctionsAndWinners() {
        List<Auction> auctions = auctionWinnerDAO.getEndedAuctions();
        System.out.println(auctions);
    }
}