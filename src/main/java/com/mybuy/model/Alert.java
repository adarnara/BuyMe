package com.mybuy.model;

public class Alert {
    private int userId;
    private double highestBid;
    private int auctionId;

    public Alert(int userId, double highestBid, int auctionId) {
        this.userId = userId;
        this.highestBid = highestBid;
        this.auctionId = auctionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(double highestBid) {
        this.highestBid = highestBid;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }
}
