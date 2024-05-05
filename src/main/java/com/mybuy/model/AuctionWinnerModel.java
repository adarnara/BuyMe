package com.mybuy.model;

import com.mybuy.dao.AuctionWinnerDAO;
import com.mybuy.utils.AlertScheduler;
import java.util.concurrent.TimeUnit;


import java.util.ArrayList;
import java.util.List;

public class AuctionWinnerModel {
    private AuctionWinnerDAO auctionWinnerDAO;

    public AuctionWinnerModel() {
        auctionWinnerDAO = new AuctionWinnerDAO();
    }

    public Auction getEndedAuction() {
        return auctionWinnerDAO.getEndedAuction();
    }

    public AuctionWinner doesAuctionHaveWinner(Auction auction) {
        Bid bid = auctionWinnerDAO.getHighestBid(auctionWinnerDAO.getEndedAuction().getAuctionId());

        if(bid == null || auction.getMinimum() > bid.getBidAmount()) {
            auctionWinnerDAO.updateEndedAuctionNoWinner(auction);
            return null;
        }

        return new AuctionWinner(auction, bid);
    }

    public void updateAuctionDetails(AuctionWinner auctionWinner) {
        if(auctionWinnerDAO.updateEndedAuction(auctionWinner)) {
            System.out.println("Successfully updated auction #" + auctionWinner.getAuction().getAuctionId());
            return;
        }
        System.out.println("Could not update auction #" + auctionWinner.getAuction().getAuctionId());
    }
}