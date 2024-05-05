package com.mybuy.dao;

import com.mybuy.model.Alert;
import com.mybuy.model.Auction;

public interface IAlertDAO {
    void postAuctionWinnerAlert(int userID, String message, int auctionID);
//    void postAuctionCloseAlert(Auction auction);
    Alert getNewAlert(int userID);
    void closeAlert(Alert alert);
}
