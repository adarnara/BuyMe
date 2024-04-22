package com.mybuy.dao;

import com.mybuy.model.AutoBid;
import java.sql.SQLException;

public interface IAutoBidDAO {
    boolean placeAutoBid(AutoBid autoBid, double bidAmount) throws SQLException;
    double[] fetchAuctionDetails(int auctionId) throws SQLException;
}
