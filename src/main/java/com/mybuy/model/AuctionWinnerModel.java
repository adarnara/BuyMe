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

        if(auctions.isEmpty()) {
            return;
        }

        for (Auction auction: auctions) {
            Bid bid = auctionWinnerDAO.getHighestBid(auction.getAuctionId());

            // TODO: add alert to seller for no bids - give option to extend auction or cancel auction?
            if(bid == null) {
                return;
            }
        }
    }
}