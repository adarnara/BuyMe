package com.mybuy.dao;

import com.mybuy.model.Bid;
import java.sql.SQLException;

public interface IBidDAO {
    Bid fetchCurrentHighestBid(int auctionId) throws SQLException;
    double fetchUserHighestBid(int auctionId, int userId) throws SQLException;
    double fetchAuctionBidIncrement(int auctionId) throws SQLException;
    boolean placeBid(Bid bid) throws SQLException;
}
