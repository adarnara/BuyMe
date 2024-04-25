package com.mybuy.dao;

import com.mybuy.model.Auction;

import java.util.List;

public interface IAuctionWinnerDAO {
    List<Auction> getEndedAuctions();
}
