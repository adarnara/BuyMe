package com.mybuy.model;

public class Bid {
    private int userId;
    private int auctionId;
    private double bidAmount;

    public Bid(int userId, int auctionId, double bidAmount) {
        this.userId = userId;
        this.auctionId = auctionId;
        this.bidAmount = bidAmount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }
}
