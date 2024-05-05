package com.mybuy.model;

import java.util.Date;

public class Alert {
    private int alertID;
    private int userID;
    private String message;
    private String status;
    private int auctionId;

    public Alert(int alertID, String message) {
        this.alertID = alertID;
        this.message = message;
    }

    public Alert(int alertId, String message, String status, int auctionId) {
    }

    public int getAlertID() {
        return alertID;
    }

    public void setAlertID(int alertID) {
        this.alertID = alertID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }
}
