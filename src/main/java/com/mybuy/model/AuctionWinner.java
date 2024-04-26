package com.mybuy.model;

public class AuctionWinner {
    private Auction auction;
    private Bid winningBid;

    public AuctionWinner(Auction auction, Bid winningBid) {
        this.auction = auction;
        this.winningBid = winningBid;
    }

    public Auction getAuction() {
        return auction;
    }

    public Bid getWinningBid() {
        return winningBid;
    }
}
