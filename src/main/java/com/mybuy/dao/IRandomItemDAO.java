package com.mybuy.dao;

import com.mybuy.model.Search;
import java.util.List;

public interface IRandomItemDAO {
    List<Search> fetchRandomItems();
}
