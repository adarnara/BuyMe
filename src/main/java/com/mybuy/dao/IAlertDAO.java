package com.mybuy.dao;

public interface IAlertDAO {
    void postAuctionWinnerAlert(int userID, String message, int auctionID);
}
