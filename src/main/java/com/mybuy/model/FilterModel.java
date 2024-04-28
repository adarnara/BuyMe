package com.mybuy.model;

import com.mybuy.dao.FilterDAO;
import com.mybuy.dao.IFilterDAO;
import java.util.List;

public class FilterModel {

    private IFilterDAO filterDAO;

    public FilterModel() {
        filterDAO = new FilterDAO();
    }

    public List<FilterOption> getFilterOptions(String optionType) {
        return filterDAO.getFilterOptions(optionType);
    }
}