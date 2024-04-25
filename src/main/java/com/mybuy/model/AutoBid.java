package com.mybuy.model;

public class AutoBid {
    private int userId;
    private int auctionId;
    private double maxAutoBidAmount;
    private Double userBidIncrement;

    public AutoBid(int userId, int auctionId, double maxAutoBidAmount, Double userBidIncrement) {
        this.userId = userId;
        this.auctionId = auctionId;
        this.maxAutoBidAmount = maxAutoBidAmount;
        this.userBidIncrement = userBidIncrement;
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

    public Double getUserBidIncrement() {
        return userBidIncrement;
    }
}
