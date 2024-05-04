package com.mybuy.dao;

import com.mybuy.model.FilterOption;
import com.mybuy.utils.ApplicationDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FilterDAO implements IFilterDAO {

    @Override
    public List<FilterOption> getFilterOptions(String optionType) {
        List<FilterOption> options = new ArrayList<>();
        String sqlQuery = buildSqlQueryForOptionType(optionType);
        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlQuery)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                options.add(new FilterOption(rs.getString(1)));
            }
        } catch (Exception e) {
            System.err.println("Error retrieving filter options: " + e.getMessage());
            e.printStackTrace();
        }
        return options;
    }

    private String buildSqlQueryForOptionType(String optionType) {
        switch (optionType) {
            case "CategoryName":
                return "SELECT DISTINCT Category_Name FROM Category ORDER BY Category_Name";
            case "SubCategoryName":
                return "SELECT DISTINCT Category_Name FROM Category WHERE Parent_Category_ID IS NOT NULL ORDER BY Category_Name";
            case "ItemName":
                return "SELECT DISTINCT name FROM Items ORDER BY name";
            case "ItemBrand":
                return "SELECT DISTINCT brand FROM Items ORDER BY brand";
            case "ColorVariants":
                return "SELECT DISTINCT color FROM Items ORDER BY color";
            case "AuctionStatus":
                return "SELECT DISTINCT auction_status FROM Auction ORDER BY auction_status";
            default:
                throw new IllegalArgumentException("Invalid filter option type: " + optionType);
        }
    }
}