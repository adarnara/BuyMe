package com.mybuy.model;

import com.mybuy.dao.ISearchDAO;
import com.mybuy.dao.SearchDAO;
import java.util.List;

public class SearchModel {
    private ISearchDAO searchDAO;

    public SearchModel() {
        searchDAO = new SearchDAO();
    }

    public List<Search> searchItems(String query) {
        return searchDAO.searchByCriteria(query);
    }
}