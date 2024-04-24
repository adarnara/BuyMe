package com.mybuy.model;

public class Search {
    private int itemId;
    private String brand;
    private String name;
    private String categoryName;
    private double currentPrice;
    private int auctionId;
    private String auctionStatus;

    public Search() {
    }

    public Search(int itemId, String brand, String name, String categoryName, double currentPrice, int auctionId, String auctionStatus) {
        this.itemId = itemId;
        this.brand = brand;
        this.name = name;
        this.categoryName = categoryName;
        this.currentPrice = currentPrice;
        this.auctionId = auctionId;
        this.auctionStatus = auctionStatus;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public String getAuctionStatus() {
        return auctionStatus;
    }

    public void setAuctionStatus(String auctionStatus) {
        this.auctionStatus = auctionStatus;
    }
}
