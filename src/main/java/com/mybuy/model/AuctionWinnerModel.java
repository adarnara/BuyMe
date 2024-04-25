package com.mybuy.model;

import com.mybuy.dao.AuctionWinnerDAO;

import java.util.ArrayList;
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

        }
    }

    // TODO: add alerts for winners and sellers, update auction details to completed & with winner userId
    private void auctionWinner() {

    }

    private void updateAuctionDetails() {

    }
}