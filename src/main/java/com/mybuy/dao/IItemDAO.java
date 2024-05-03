package com.mybuy.dao;

import com.mybuy.model.Item;

public interface IItemDAO {
    public int getCategoryId(String categoryName);
    public Item getItemById(int itemId);
    public int addItem(Item newItem);
}
