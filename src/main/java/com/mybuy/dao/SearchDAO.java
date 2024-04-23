package com.mybuy.dao;

import com.mybuy.model.Search;
import com.mybuy.utils.ApplicationDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchDAO implements ISearchDAO {

    @Override
    public List<Search> searchByCriteria(String query) {
        List<Search> searchResults = new ArrayList<>();
        String sqlQueryStmt = "SELECT i.Item_ID, i.brand, i.name, c.Category_Name, " +
                "GREATEST(LEVENSHTEIN_RATIO(i.name, ?), LEVENSHTEIN_RATIO(i.brand, ?), LEVENSHTEIN_RATIO(c.Category_Name, ?)) AS similarity " +
                "FROM Items i " +
                "INNER JOIN Category c ON i.Category_ID = c.Category_ID " +
                "WHERE (MATCH(i.brand, i.name) AGAINST (? IN BOOLEAN MODE) OR " +
                "MATCH(c.Category_Name) AGAINST (? IN BOOLEAN MODE)) AND " +
                "(LEVENSHTEIN_RATIO(i.name, ?) > 50 OR " +
                "LEVENSHTEIN_RATIO(i.brand, ?) > 50 OR " +
                "LEVENSHTEIN_RATIO(c.Category_Name, ?) > 50) " +
                "ORDER BY similarity DESC;";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlQueryStmt)) {

            pstmt.setString(1, query);
            pstmt.setString(2, query);
            pstmt.setString(3, query);
            pstmt.setString(4, query + "*");
            pstmt.setString(5, query + "*");
            pstmt.setString(6, query);
            pstmt.setString(7, query);
            pstmt.setString(8, query);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Search search = new Search();
                    search.setItemId(rs.getInt("Item_ID"));
                    search.setBrand(rs.getString("brand"));
                    search.setName(rs.getString("name"));
                    search.setCategoryName(rs.getString("Category_Name"));
                    searchResults.add(search);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error during search: " + e.getMessage());
            e.printStackTrace();
        }
        return searchResults;
    }
}
