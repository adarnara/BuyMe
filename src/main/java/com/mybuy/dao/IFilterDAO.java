package com.mybuy.dao;

import com.mybuy.model.FilterOption;

import java.util.List;

public interface IFilterDAO {
    List<FilterOption> getFilterOptions(String optionType);
}