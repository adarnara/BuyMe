package com.mybuy.model;

public class AutoBid {
    private int userId;
    private int auctionId;
    private double maxAutoBidAmount;

    public AutoBid(int userId, int auctionId, double maxAutoBidAmount) {
        this.userId = userId;
        this.auctionId = auctionId;
        this.maxAutoBidAmount = maxAutoBidAmount;
    }

    public int getUserId() {
        return userId;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public double getMaxAutoBidAmount() {
        return maxAutoBidAmount;
    }
}
