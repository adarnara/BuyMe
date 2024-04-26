package com.mybuy.model;

public class TopThings {
	private String name;
	private double totalSales;
	
	public TopThings(String username, double totalSales) {
		this.name = username;
		this.totalSales = totalSales;
	}

	public String getUsername() {
		return name;
	}

	public void setUsername(String username) {
		this.name = username;
	}

	public double getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(double totalSales) {
		this.totalSales = totalSales;
	}

}
