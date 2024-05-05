package com.mybuy.model;

import com.mybuy.dao.AlertDAO;
import com.mybuy.dao.IAlertDAO;

public class AlertModel {
    private IAlertDAO alertDAO;
    private LoginModel loginModel;

    public AlertModel() {
        this.alertDAO = new AlertDAO();
        loginModel = new LoginModel();
    }

    public void auctionWinnerAlert(AuctionWinner auctionWinner) {
        Auction auction = auctionWinner.getAuction();

        int userID = auctionWinner.getWinningBid().getUserId();
        String message = "Congratulations! You have won auction #" + auction.getAuctionId();
        int auctionId = auction.getAuctionId();

        alertDAO.postAuctionWinnerAlert(userID, message, auctionId);
    }

    public void auctionCloseAlert(Auction auction) {

    }

    public Alert newAlertById(int userId) {
        Alert alert = alertDAO.getNewAlert(userId);
        if(alert != null) {
            alertDAO.closeAlert(alert);
        }

        return alert;
    }
}
