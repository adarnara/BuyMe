package com.mybuy.model;

public class Search {
    private int itemId;
    private String brand;
    private String name;
    private String categoryName;

    public Search() {
    }

    public Search(int itemId, String brand, String name, String categoryName) {
        this.itemId = itemId;
        this.brand = brand;
        this.name = name;
        this.categoryName = categoryName;
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
}