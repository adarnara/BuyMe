package com.mybuy.dao;

import com.mybuy.model.Bid;


public interface IBidDAO {
    boolean placeBid(Bid bid);
    boolean updateBid(int bidId, double newBidAmount);

}