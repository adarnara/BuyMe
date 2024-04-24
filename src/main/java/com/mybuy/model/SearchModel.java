package com.mybuy.model;

import com.mybuy.dao.ISearchDAO;
import com.mybuy.dao.SearchDAO;
import java.util.List;
import java.util.Map;

public class SearchModel {
    private ISearchDAO searchDAO;

    public SearchModel() {
        searchDAO = new SearchDAO();
    }

    public List<Search> searchItems(String query) {
        return searchDAO.searchByCriteria(query);
    }

    public List<Search> filterItems(Map<String, String> filters) {
        return searchDAO.filterByFields(filters);
    }

    public List<Search> filterAndFuzzySearchItems(Map<String, String> filters, String fuzzyQuery) {
        return searchDAO.filterAndFuzzySearch(filters, fuzzyQuery);
    }
}
