package com.mybuy.dao;

import com.mybuy.model.Search;
import com.mybuy.utils.ApplicationDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RandomItemDAO implements IRandomItemDAO {

    @Override
    public List<Search> fetchRandomItems() {
        String sql = "SELECT i.Item_ID, i.brand, i.name, c.Category_Name, a.Current_Price, a.Auction_ID, a.auction_status " +
                "FROM Items i " +
                "JOIN Category c ON i.Category_ID = c.Category_ID " +
                "JOIN Auction a ON i.Item_ID = a.Item_ID " +
                "ORDER BY RAND() LIMIT 6;";

        List<Search> items = new ArrayList<>();
        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                items.add(new Search(
                        rs.getInt("Item_ID"),
                        rs.getString("brand"),
                        rs.getString("name"),
                        rs.getString("Category_Name"),
                        rs.getDouble("Current_Price"),
                        rs.getInt("Auction_ID"),
                        rs.getString("auction_status")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error during random item fetch: " + e.getMessage());
            e.printStackTrace();
        }
        return items;
    }
}
