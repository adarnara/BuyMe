package com.mybuy.model;

import com.mybuy.dao.AutoBidDAO;
import com.mybuy.dao.IAutoBidDAO;
import com.mybuy.utils.AutoBidScheduler;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class AutoBidModel {
    private IAutoBidDAO autoBidDAO;
    private AutoBidScheduler scheduler;

    public AutoBidModel() {
        this.autoBidDAO = new AutoBidDAO();
        this.scheduler = new AutoBidScheduler();
    }

    public boolean processAutoBid(AutoBid autoBid) {
        try {
            scheduler.scheduleAutoBid(() -> {
                try {
                    double[] auctionDetails = autoBidDAO.fetchAuctionDetails(autoBid.getAuctionId());
                    if (auctionDetails != null) {
                        double nextBid = getNextBid(auctionDetails, autoBid.getUserId(), autoBid.getMaxAutoBidAmount());

                        if (nextBid > 0) {
                            autoBidDAO.placeAutoBid(autoBid, nextBid);
                            autoBidDAO.updateCurrentPrice(autoBid.getAuctionId(), nextBid);
                        }
                    }
                } catch (SQLException e) {
                    System.err.println("Error in auto-bidding: " + e.getMessage());
                }
            }, 1, TimeUnit.MINUTES);

            return true;
        } catch (Exception e) {
            System.err.println("Failed to schedule auto-bid: " + e.getMessage());
            return false;
        }
    }

    private double getNextBid(double[] auctionDetails, int userId, double maxAutoBidAmount) {
        double currentPrice = auctionDetails[0];
        double bidIncrement = auctionDetails[1];
        int lastBidUserId = (int) auctionDetails[2];
        double nextBid = 0;

        if (lastBidUserId != userId) {
            if (lastBidUserId == 0) {
                nextBid = currentPrice;
            } else {
                nextBid = currentPrice + bidIncrement;
            }

            if (nextBid > maxAutoBidAmount) {
                nextBid = 0;
            }
        }
        return nextBid;
    }
}
