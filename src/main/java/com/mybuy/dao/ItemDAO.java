package com.mybuy.dao;

import com.mybuy.model.Auction;
import com.mybuy.model.Item;
import com.mybuy.utils.ApplicationDB;

import java.sql.*;

public class ItemDAO implements IItemDAO {
    @Override
    public int getCategoryId(String categoryName) {
        int categoryId = -1;
        String sql = "SELECT Category_ID FROM Category WHERE Category_Name = ?";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, categoryName);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    categoryId = rs.getInt("Category_ID");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching category ID by name: " + e.getMessage());
        }

        return categoryId;
    }

    @Override
    public int addItem(Item newItem) {
        String sql = "INSERT INTO Items (Category_ID, brand, name, color) VALUES (?, ?, ?, ?)";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, newItem.getCategoryId());
            pstmt.setString(2, newItem.getBrand());
            pstmt.setString(3, newItem.getName());
            pstmt.setString(4, newItem.getColor());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Error adding auction: " + e.getMessage());
        }

        return -1;
    }


}
