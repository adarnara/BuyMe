package com.mybuy.model;
import java.util.Date;

public class Auction {
    private int auctionId;
    private double initialPrice;
    private double currentPrice;
    private Date closingDate;
    private Date closingTime;
    private double bidIncrement;
    private double minimum;
    private int winner;
    private int userId;
    private int itemId;
    private String status;
    private String winnerUsername;
    private Item item;

    // Retrieving auction details
    public Auction(int auctionId, double initialPrice, double currentPrice, Date closingDate, Date closingTime, double bidIncrement, double minimum, int winner, int userId, int itemId, String status) {
        this.auctionId = auctionId;
        this.initialPrice = initialPrice;
        this.currentPrice = currentPrice;
        this.closingDate = closingDate;
        this.closingTime = closingTime;
        this.bidIncrement = bidIncrement;
        this.minimum = minimum;
        this.winner = winner;
        this.userId = userId;
        this.itemId = itemId;
        this.status = status;
    }

    // Creating new Auction
    public Auction(Date closingDate, Date closingTime, double initialPrice, double bidIncrement, double minimum, int userId, int itemId) {
        this.initialPrice = initialPrice;
        this.currentPrice = initialPrice;
        this.bidIncrement = bidIncrement;
        this.closingDate = closingDate;
        this.closingTime = closingTime;
        this.minimum = minimum;
        this.userId = userId;
        this.itemId = itemId;
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

    public int getUserId() {
        return userId;
    }

    public String getStatus() { return status; };

    public int getItemId() {
        return itemId;
    }

    public int getWinner() {
        return winner;
    }
    public void setWinnerUsername(String winnerUsername) {
        this.winnerUsername = winnerUsername;
    }
    public String getWinnerUsername() {
        return winnerUsername;
    }

    public double getBidIncrement() {
        return bidIncrement;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
