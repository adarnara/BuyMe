package com.mybuy.model;

import com.mybuy.dao.AuctionWinnerDAO;
import com.mybuy.utils.AlertScheduler;
import java.util.concurrent.TimeUnit;


import java.util.ArrayList;
import java.util.List;

public class AuctionWinnerModel {
    private AuctionWinnerDAO auctionWinnerDAO;
    private AlertScheduler alertScheduler;
    private int winnerAuctionId = -1;


    public AuctionWinnerModel() {

        auctionWinnerDAO = new AuctionWinnerDAO();
        alertScheduler = new AlertScheduler();
        alertScheduler.scheduleTask(this::updateAuctions, 0, 1, TimeUnit.MINUTES);
    }

    public void getEndedAuctionsAndWinners() {
        List<Auction> auctions = auctionWinnerDAO.getEndedAuctions();
        if(auctions.isEmpty()) {
            return;
        }

        // TODO: double check this
        // Frances: I'm choosing to process auctions in batches to avoid overwhelming the system/scheduled timer to check for ended auctions
        int batchSize = 5; // We can adjust the batch size as needed, for now I'm just doing 5 auctions at a time
        for (int i = 0; i < auctions.size(); i += batchSize) {
            List<Auction> batch = auctions.subList(i, Math.min(i + batchSize, auctions.size()));
            processAuctionBatch(batch);
        }
    }

    private void processAuctionBatch(List<Auction> auctions) {
        for (Auction auction: auctions) {
            Bid bid = auctionWinnerDAO.getHighestBid(auction.getAuctionId());

            // TODO: add alert to seller for no bids - give option to extend auction or cancel auction?
            if(bid == null) {
                return;
            }

            // TODO: add alert to seller that highest bid < reserve, give option to extend auction, cancel, or change reserve?
            if(auction.getMinimum() > bid.getBidAmount()) {
                return;
            }

            AuctionWinner auctionWinner = new AuctionWinner(auction, bid);
            updateAuctionDetails(auctionWinner);
            // TODO: add alerts to winner + for seller, reload page
        }
    }

    private void updateAuctions() {
        List<Auction> auctions = auctionWinnerDAO.getEndedAuctions();
        for (Auction auction : auctions) {
            int auctionId = auctionWinnerDAO.isUserWinner(auction.getWinner());
            if (auctionId != -1) {
                winnerAuctionId = auctionId;
                break;
            }
        }
    }

    public int getAuctionIdIfUserWon(int userId) {
        return auctionWinnerDAO.isUserWinner(userId);
    }

    public void shutdownScheduler() {
        alertScheduler.shutdown();
    }

    // TODO: take out system print lines
    private void updateAuctionDetails(AuctionWinner auctionWinner) {
        if(auctionWinnerDAO.updateEndedAuction(auctionWinner)) {
            System.out.println("Successfully updated auction #" + auctionWinner.getAuction().getAuctionId());
            return;
        }
        System.out.println("Could not update auction #" + auctionWinner.getAuction().getAuctionId());
    }
}