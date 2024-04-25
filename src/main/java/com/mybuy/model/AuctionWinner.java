package com.mybuy.model;

public class AuctionWinner {
    private int auctionId;
    private int userId;

    public AuctionWinner(int auctionId, int userId) {
        this.auctionId = auctionId;
        this.userId = userId;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public int getUserId() {
        return userId;
    }
}
