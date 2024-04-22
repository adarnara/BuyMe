package com.mybuy.model;
import java.util.Date;

public class Auction {
    private int auctionId;
    private double currentPrice;
    private Date closingDate;
    private Date closingTime;
    private double bidIncrement; // TODO TAKE OUT BID INCREMENT
    private double initialPrice;
    private double minimum;
    private double upperLimit; // TODO TAKE OUT UPPER LIMIT
    private String winner;
    private int userId;
    private int itemId;

    public Auction(int auctionId, Date closingDate, Date closingTime, double bidIncrement, double initialPrice, int userId, int itemId) {
        this.auctionId = auctionId;
        this.initialPrice = initialPrice;
        this.currentPrice = initialPrice;
        this.closingDate = closingDate;
        this.closingTime = closingTime;
        this.bidIncrement = bidIncrement;
        this.userId = userId;
        this.itemId = itemId;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    public void setUpperLimit(double upperLimit) {
        this.upperLimit = upperLimit;
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

    public double getBidIncrement() {
        return bidIncrement;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public double getMinimum() {
        return minimum;
    }

    public double getUpperLimit() {
        return upperLimit;
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
