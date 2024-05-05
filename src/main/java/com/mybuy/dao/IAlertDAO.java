package com.mybuy.dao;

import com.mybuy.model.Alert;

public interface IAlertDAO {
    void postAuctionWinnerAlert(int userID, String message, int auctionID);
    Alert getNewAlert(int userID);
    void closeAlert(Alert alert);
}
