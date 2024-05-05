package com.mybuy.model;

import com.mybuy.dao.IRandomItemDAO;
import com.mybuy.dao.RandomItemDAO;

import java.util.List;

public class RandomItemModel {
    private IRandomItemDAO randomItemDAO;

    public RandomItemModel() {
        randomItemDAO = new RandomItemDAO();
    }

    public List<Search> getRandomItems() {
        return randomItemDAO.fetchRandomItems();
    }
}
