package com.mybuy.dao;

import com.mybuy.model.Search;
import java.util.List;
import java.util.Map;

public interface ISearchDAO {
    List<Search> searchByCriteria(String query);
    List<Search> filterByFields(Map<String, String> filters);
    List<Search> filterAndFuzzySearch(Map<String, String> filters, String fuzzyQuery);
}