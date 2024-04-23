package com.mybuy.dao;

import com.mybuy.model.Search;
import java.util.List;

public interface ISearchDAO {
    List<Search> searchByCriteria(String query);
}