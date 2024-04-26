package com.mybuy.model;

import com.mybuy.dao.SalesReportDAO;

import java.util.List;

import com.mybuy.dao.IRegisterDAO;
import com.mybuy.dao.ISalesReportDAO;
import com.mybuy.dao.RegisterDAO;

public class SalesReportModel {

	private ISalesReportDAO salesReportDAO;
	
	public SalesReportModel() {
		this.salesReportDAO = new SalesReportDAO();
	}
	
	public double getEarningsData() {
		return salesReportDAO.getTotalSales();
	}
	
	public List<TopThings> getTopBuyers(int limit) {
		return salesReportDAO.getTopBuyers(limit);
	}
	
	public List<TopThings> getTopItems(int limit) {
		return salesReportDAO.getTopItems(limit);
	}
	
    public List<TopThings> getPerItem() {
    	return salesReportDAO.perItem();
    }
    
    public List<TopThings> getPerBuyer() {
    	return salesReportDAO.perBuyer();
    }
    
    public List<TopThings> getPerCategory() {
    	return salesReportDAO.perCategory();
    }
}