package com.mybuy.model;

import com.mybuy.dao.AlertDAO;
import com.mybuy.dao.AutoBidDAO;
import com.mybuy.dao.IAutoBidDAO;
import com.mybuy.utils.AutoBidScheduler;
import java.sql.SQLException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ConcurrentHashMap;

public class AutoBidModel {
    private IAutoBidDAO autoBidDAO;
    private AlertDAO alertDAO;
    private AutoBidScheduler scheduler;
    private ConcurrentHashMap<Integer, Future<?>> scheduledTasks;

    public AutoBidModel() {
        this.autoBidDAO = new AutoBidDAO();
        this.alertDAO = new AlertDAO();
        this.scheduler = new AutoBidScheduler();
        this.scheduledTasks = new ConcurrentHashMap<>();
    }

    public boolean processAutoBid(AutoBid autoBid) {
        try {
            Future<?> future = scheduler.scheduleAutoBid(() -> {
                try {
                    double[] auctionDetails = autoBidDAO.fetchAuctionDetails(autoBid.getAuctionId());
                    if (auctionDetails != null) {
                        double nextBid = getNextBid(auctionDetails, autoBid.getUserId(), autoBid.getMaxAutoBidAmount(), autoBid.getUserBidIncrement());

                        if (nextBid == 0) {
                            deactivateAutoBid(autoBid.getAuctionId());
                            return;
                        }
                        autoBidDAO.placeAutoBid(autoBid, nextBid);
                        autoBidDAO.updateCurrentPrice(autoBid.getAuctionId(), nextBid);

                        alertDAO.postExceedAutoBidAlert(autoBid.getAuctionId(), nextBid, autoBid.getUserId());

                    }
                } catch (SQLException e) {
                    System.err.println("Error in auto-bidding: " + e.getMessage());
                }
            }, 30, TimeUnit.SECONDS);

            scheduledTasks.put(autoBid.getAuctionId(), future);
            return true;
        } catch (Exception e) {
            System.err.println("Failed to schedule auto-bid: " + e.getMessage());
            return false;
        }
    }

    private void deactivateAutoBid(int auctionId) {
        Future<?> task = scheduledTasks.get(auctionId);
        if (task != null && !task.isCancelled()) {
            task.cancel(false);
            scheduledTasks.remove(auctionId);
        }
    }

    private double getNextBid(double[] auctionDetails, int userId, double maxAutoBidAmount, Double userBidIncrement) {
        double currentPrice = auctionDetails[0];
        double dbBidIncrement = auctionDetails[1];
        int lastBidUserId = (int) auctionDetails[2];
        double nextBid = 0;
        double effectiveBidIncrement = (userBidIncrement != null && userBidIncrement >= dbBidIncrement) ? userBidIncrement : dbBidIncrement;

        if (lastBidUserId != userId) {
            if (lastBidUserId == 0) {
                nextBid = currentPrice;
            } else {
                nextBid = currentPrice + effectiveBidIncrement;
            }

            if (nextBid > maxAutoBidAmount) {
                return 0;
            }
        }
        return nextBid;
    }

    public boolean isCurrentPriceExceedingLimit(int auctionId, int userId, double upperLimit) {
        scheduledTasks.computeIfAbsent(auctionId, k -> scheduler.scheduleAtFixedRate(() -> {
            try {
                double currentPrice = autoBidDAO.fetchCurrentPriceIfNotByUser(auctionId, userId);
                if (currentPrice > upperLimit && currentPrice != -1) {
                    System.out.println("Alert: Current price exceeds your set upper limit for auction ID " + auctionId);
                    deactivateAutoBid(auctionId);
                }
            } catch (SQLException e) {
                System.err.println("Error in scheduled price check: " + e.getMessage());
            }
        }, 0, 1, TimeUnit.MINUTES));

        return true;
    }
}
