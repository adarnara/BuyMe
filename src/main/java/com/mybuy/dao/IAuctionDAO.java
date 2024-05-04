package com.mybuy.dao;

import com.mybuy.model.Auction;

import java.util.List;

public interface IAuctionDAO {
    Auction getAuctionById(int auctionID);
    List<Auction> getAuctionsByUsername(String username);
    int addAuction(Auction auction);
    List<Auction> getSimilarAuctionsByItemId(int itemId);
}
