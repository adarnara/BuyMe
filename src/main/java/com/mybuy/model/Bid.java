package com.mybuy.model;

import java.util.Date;

public class Bid {
    private int userId;
    private int auctionId;
    private double bidAmount;
    private Date bidDate;
    private Date bidTime;
    private String username;

    public Bid(int userId, int auctionId, double bidAmount) {
        this.userId = userId;
        this.auctionId = auctionId;
        this.bidAmount = bidAmount;
    }

    // For getting bid info for history of bid
    public Bid(String username, Date bidDate, Date bidTime, double bidAmount) {
        this.username = username;
        this.bidDate = bidDate;
        this.bidTime = bidTime;
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

    public Date getBidDate() {
        return bidDate;
    }

    public Date getBidTime() {
        return bidTime;
    }

    public String getUsername() {
        return username;
    }
}