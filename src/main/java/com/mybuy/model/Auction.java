package com.mybuy.model;
import java.util.Date;

public class Auction {
    private int auctionId;
    private double currentPrice;
    private Date closingDate;
    private Date closingTime;
    private double initialPrice;
    private double minimum;
    private String winner;
    private int userId;
    private int itemId;

    public Auction(int auctionId, Date closingDate, Date closingTime, double initialPrice, double currentPrice, int userId, int itemId) {
        this.auctionId = auctionId;
        this.initialPrice = initialPrice;
        this.currentPrice = currentPrice;
        this.closingDate = closingDate;
        this.closingTime = closingTime;
        this.userId = userId;
        this.itemId = itemId;
    }

    // Creating new Auction
    public Auction(Date closingDate, Date closingTime, double initialPrice, double minimum, int userId, int itemId) {
        this.initialPrice = initialPrice;
        this.currentPrice = initialPrice;
        this.closingDate = closingDate;
        this.closingTime = closingTime;
        this.minimum = minimum;
        this.userId = userId;
        this.itemId = itemId;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public Date getAuctionClosingDate() {
        return closingDate;
    }

    public Date getAuctionClosingTime() {
        return closingTime;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public double getMinimum() {
        return minimum;
    }

    public String getWinner() {
        return winner;
    }

    public int getUserId() {
        return userId;
    }

    public int getItemId() {
        return itemId;
    }
}
