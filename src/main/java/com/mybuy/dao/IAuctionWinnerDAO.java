package com.mybuy.dao;

import com.mybuy.model.Auction;
import com.mybuy.model.AuctionWinner;
import com.mybuy.model.Bid;

import java.util.List;

public interface IAuctionWinnerDAO {
    List<Auction> getEndedAuctions();
    Bid getHighestBid(int auctionId);
    boolean updateEndedAuction(AuctionWinner auctionWinner);
}
