package com.mybuy.model;

import com.mybuy.dao.AlertDAO;
import com.mybuy.dao.IAlertDAO;

import java.util.List;

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
        int userID = auction.getUserId();
        String message = "Your action #" + auction.getAuctionId() + " has closed. There was no winner.";

        if(auction.getWinnerUsername() != null) {
            message = "Your action #" + auction.getAuctionId() + " has closed. The winner was " + auction.getWinnerUsername() + "!";
        }

        alertDAO.postAuctionCloseAlert(userID, message, auction);
    }

    public Alert newAlertById(int userId) {
        Alert alert = alertDAO.getNewAlert(userId);
        if(alert != null) {
            alertDAO.closeAlert(alert);
        }

        return alert;
    }

    public List<Alert> getBidAlertsForUser(int userId) {
        return alertDAO.getBidAlertsForUser(userId);
    }
    public void postBidAlert(int auctionId, String message, int userId) {
        alertDAO.postBidAlert(auctionId, message, userId);
    }

    public void handleAutoBidExceedAlert(int auctionId, double bidAmount, int userId) {
        alertDAO.postExceedAutoBidAlert(auctionId, bidAmount, userId);
    }

    public List<Alert> getExceedAutoBidAlertsForUser(int userId) {
        return alertDAO.getExceedAutoBidAlertsForUser(userId);
    }

    public void checkAndNotifyAlerts(Auction auction) {
        alertDAO.updateAlertsForNewAuction(auction);
    }
}
