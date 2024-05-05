package com.mybuy.dao;

import com.mybuy.model.Alert;
import com.mybuy.model.Auction;

import java.util.List;

public interface IAlertDAO {
    void postAuctionWinnerAlert(int userID, String message, int auctionID);
    void postAuctionCloseAlert(int userID, String message, Auction auction);
    Alert getNewAlert(int userID);
    void closeAlert(Alert alert);
    void postBidAlert(int auctionId, String message, int userIdWhoPlacedBid);
    List<Alert> getBidAlertsForUser(int userId);
}
