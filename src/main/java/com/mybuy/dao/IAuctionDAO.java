package com.mybuy.dao;

import com.mybuy.model.Auction;

public interface IAuctionDAO {
    Auction getAuctionById(int auctionID);
}
