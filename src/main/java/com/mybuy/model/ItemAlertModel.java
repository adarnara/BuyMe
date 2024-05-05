package com.mybuy.model;

import com.mybuy.dao.ItemAlertDAO;
import com.mybuy.dao.IItemAlertDAO;

public class ItemAlertModel {
    private IItemAlertDAO itemAlertDAO;

    public ItemAlertModel() {
        itemAlertDAO = new ItemAlertDAO();
    }

    public boolean setItemAlert(ItemAlert itemAlert) {
        return itemAlertDAO.insertItemAlert(itemAlert);
    }
}
