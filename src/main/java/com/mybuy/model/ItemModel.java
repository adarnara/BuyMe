package com.mybuy.model;

import com.mybuy.dao.IItemDAO;
import com.mybuy.dao.ItemDAO;
import com.mybuy.utils.ApplicationDB;

import java.sql.*;

public class ItemModel {
    private IItemDAO itemDAO;

    public ItemModel() {
        this.itemDAO = new ItemDAO();
    }

    private int getCategoryID(String categoryName) {
        return itemDAO.getCategoryId(categoryName);
    }

    public Item createNewItem(String itemCategory, String itemBrand, String itemName, String itemColor) {
        int categoryId = getCategoryID(itemCategory);

        return new Item(
                categoryId,
                itemBrand,
                itemName,
                itemColor
        );
    }

    public int addItem(Item newItem) {
        return itemDAO.addItem(newItem);
    }

}
