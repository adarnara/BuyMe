package com.mybuy.dao;

import java.util.List;
import com.mybuy.model.TopThings;

public interface ISalesReportDAO {
	double getTotalSales();
    List<TopThings> getTopBuyers(int limit);
    List<TopThings> getTopItems(int limit);
    List<TopThings> perItem();
    List<TopThings> perBuyer();
    List<TopThings> perCategory();
}
