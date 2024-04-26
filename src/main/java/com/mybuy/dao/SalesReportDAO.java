package com.mybuy.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mybuy.model.TopThings;
import com.mybuy.utils.ApplicationDB;

public class SalesReportDAO implements ISalesReportDAO {

	public double getTotalSales() {
		String sql = "SELECT SUM(Current_Price) AS TotalSales FROM Auction WHERE Winner IS NOT NULL";
		double totalSales = 0;
		
        try (Connection conn = ApplicationDB.getConnection();
        	 PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	try (ResultSet rs = pstmt.executeQuery()) {
        		if (rs.next()) {
        			totalSales = rs.getDouble("TotalSales");
        		}
        	}

        } catch (SQLException e) {
                System.out.println("Couldn't get sales report");
        }
		return totalSales;
	}
	public List<TopThings> getTopItems(int limit) {
        List<TopThings> topItems = new ArrayList<>();
        String sql = "SELECT i.Item_ID, i.name AS name, SUM(a.Current_Price) AS totalSpent " +
                     "FROM Items i " +
                     "INNER JOIN Auction a ON i.Item_ID = a.Item_ID " +
                     "GROUP BY i.Item_ID " +
                     "ORDER BY totalSpent DESC " +
                     "LIMIT ?";
        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    double totalSpent = rs.getDouble("totalSpent");
                    TopThings b = new TopThings(name, totalSpent);
                    topItems.add(b);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topItems;

	}
	
	public List<TopThings> getTopBuyers(int limit) {
        List<TopThings> topBuyers = new ArrayList<>();
        String sql = "SELECT u.User_Id, u.endUser_login AS Username, SUM(a.Current_Price) AS totalSpent " +
                     "FROM EndUser u " +
                     "INNER JOIN Auction a ON u.User_ID = a.Winner " +
                     "GROUP BY u.User_ID " +
                     "ORDER BY totalSpent DESC " +
                     "LIMIT ?";
        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String username = rs.getString("Username");
                    double totalSpent = rs.getDouble("totalSpent");
                    TopThings b = new TopThings(username, totalSpent);
                    topBuyers.add(b);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topBuyers;
	}
	
	public List<TopThings> perBuyer() {
        List<TopThings> perBuyer = new ArrayList<>();
        String sql = "SELECT u.User_Id, u.endUser_login AS Username, SUM(a.Current_Price) AS totalSpent " +
                "FROM EndUser u " +
                "INNER JOIN Auction a ON u.User_ID = a.Winner " +
                "GROUP BY u.User_ID";
        try (Connection conn = ApplicationDB.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
               try (ResultSet rs = pstmt.executeQuery()) {
                   while (rs.next()) {
                       String username = rs.getString("Username");
                       double totalSpent = rs.getDouble("totalSpent");
                       TopThings b = new TopThings(username, totalSpent);
                       perBuyer.add(b);
                   }
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
        return perBuyer;
	}
	
	public List<TopThings> perItem() {
        List<TopThings> perItem = new ArrayList<>();
        String sql = "SELECT i.Item_ID, i.name AS name, SUM(a.Current_Price) AS totalSpent " +
                "FROM Items i " +
                "INNER JOIN Auction a ON i.Item_ID = a.Item_ID " +
                "GROUP BY i.Item_ID";
        try (Connection conn = ApplicationDB.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
               try (ResultSet rs = pstmt.executeQuery()) {
                   while (rs.next()) {
                       String name = rs.getString("name");
                       double totalSpent = rs.getDouble("totalSpent");
                       TopThings b = new TopThings(name, totalSpent);
                       perItem.add(b);
                   }
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
        return perItem;
	}
	
	public List<TopThings> perCategory() {
        List<TopThings> perCategory = new ArrayList<>();
        String sql = "SELECT c.Category_ID, c.Category_name AS name, SUM(a.Current_Price) AS totalSpent " +
                "FROM Category c " +
        		"INNER JOIN Items i on i.Category_ID = c.Category_ID " + 
                "INNER JOIN Auction a ON i.Item_ID = a.Item_ID " +
                "GROUP BY c.Category_ID";
        try (Connection conn = ApplicationDB.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
               try (ResultSet rs = pstmt.executeQuery()) {
                   while (rs.next()) {
                       String name = rs.getString("name");
                       double totalSpent = rs.getDouble("totalSpent");
                       TopThings b = new TopThings(name, totalSpent);
                       perCategory.add(b);
                   }
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
        return perCategory;
	}
}
