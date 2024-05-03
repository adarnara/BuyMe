package com.mybuy.model;

public class Item {
    private int itemId;
    private String brand;
    private String name;
    private String color;
    private int categoryId;
    private String imageUrl;

    // Creating new item
    public Item(int categoryId, String brand, String name, String color) {
        this.categoryId = categoryId;
        this.brand = brand;
        this.name = name;
        this.color = color;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
