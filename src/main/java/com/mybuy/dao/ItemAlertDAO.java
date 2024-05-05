package com.mybuy.dao;

import com.mybuy.model.ItemAlert;
import com.mybuy.utils.ApplicationDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemAlertDAO implements IItemAlertDAO {

    @Override
    public boolean insertItemAlert(ItemAlert itemAlert) {
        String sql = "INSERT INTO Alerts (User_ID, Message, Status, Auction_ID, Item_Name, Item_Brand, Category_Name, Color) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, itemAlert.getUserId());
            pstmt.setString(2, itemAlert.getMessage());
            pstmt.setString(3, itemAlert.getStatus());
            pstmt.setObject(4, itemAlert.getAuctionId());
            pstmt.setString(5, itemAlert.getItemName());
            pstmt.setString(6, itemAlert.getItemBrand());
            pstmt.setString(7, itemAlert.getCategoryName());
            pstmt.setString(8, itemAlert.getColorVariant());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error inserting item alert: " + e.getMessage());
            return false;
        }
    }
}
