package com.mybuy.model;

public class ItemAlert {
    private int userId;
    private String message;
    private String status;
    private Integer auctionId;
    private String itemName;
    private String itemBrand;
    private String categoryName;
    private String colorVariant;

    public ItemAlert(int userId, String message, String status, Integer auctionId, String itemName, String itemBrand, String categoryName, String colorVariant) {
        this.userId = userId;
        this.message = message;
        this.status = status;
        this.auctionId = auctionId;
        this.itemName = itemName;
        this.itemBrand = itemBrand;
        this.categoryName = categoryName;
        this.colorVariant = colorVariant;
    }

    public int getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public Integer getAuctionId() {
        return auctionId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemBrand() {
        return itemBrand;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getColorVariant() {
        return colorVariant;
    }
}
