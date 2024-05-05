package com.mybuy.model;

import java.util.Date;

public class Alert {
    private int alertID;
    private int userID;
    private String message;
    private String alertType;
    private Date createdDate;
    private String status;
    private int auctionId;

    public Alert(int alertID, String message, String alertType, Date createdDate, int auctionId) {
        this.alertID = alertID;
        this.message = message;
        this.alertType = alertType;
        this.createdDate = createdDate;
        this.auctionId = auctionId;
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

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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
