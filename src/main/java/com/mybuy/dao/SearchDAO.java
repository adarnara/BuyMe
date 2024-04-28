package com.mybuy.dao;

import com.mybuy.model.Search;
import com.mybuy.utils.ApplicationDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchDAO implements ISearchDAO {

    @Override
    public List<Search> searchByCriteria(String query) {
        List<Search> searchResults = new ArrayList<>();
        String sqlQueryStmt = "SELECT i.Item_ID, i.brand, i.name, c.Category_Name, a.Current_Price, a.Auction_ID, a.auction_status, " +
                "GREATEST(LEVENSHTEIN_RATIO(i.name, ?), LEVENSHTEIN_RATIO(i.brand, ?), LEVENSHTEIN_RATIO(c.Category_Name, ?)) AS similarity " +
                "FROM Items i " +
                "INNER JOIN Category c ON i.Category_ID = c.Category_ID " +
                "INNER JOIN Auction a ON i.Item_ID = a.Item_ID " +
                "WHERE (MATCH(i.brand, i.name) AGAINST (? IN BOOLEAN MODE) OR " +
                "MATCH(c.Category_Name) AGAINST (? IN BOOLEAN MODE)) OR " +
                "(LEVENSHTEIN_RATIO(i.name, ?) > 70 OR " +
                "LEVENSHTEIN_RATIO(i.brand, ?) > 70 OR " +
                "LEVENSHTEIN_RATIO(c.Category_Name, ?) > 70) " +
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

            getAndSetInfo(searchResults, pstmt);
        } catch (SQLException e) {
            System.out.println("Error during search: " + e.getMessage());
            e.printStackTrace();
        }
        return searchResults;
    }

    @Override
    public List<String> fuzzyAutocomplete(String prefix) {
        List<String> suggestions = new ArrayList<>();
        int similarityThreshold = 70;
        String sql = "SELECT i.brand, i.name, c.Category_Name, " +
                "LEVENSHTEIN_RATIO(i.name, ?) AS name_similarity, " +
                "LEVENSHTEIN_RATIO(i.brand, ?) AS brand_similarity, " +
                "LEVENSHTEIN_RATIO(c.Category_Name, ?) AS category_similarity " +
                "FROM Items i " +
                "JOIN Category c ON i.Category_ID = c.Category_ID " +
                "WHERE ((i.name LIKE CONCAT('%', ?, '%') OR " +
                "i.brand LIKE CONCAT('%', ?, '%') OR " +
                "c.Category_Name LIKE CONCAT('%', ?, '%')) OR " +
                "(LEVENSHTEIN_RATIO(i.name, ?) > ? OR " +
                "LEVENSHTEIN_RATIO(i.brand, ?) > ? OR " +
                "LEVENSHTEIN_RATIO(c.Category_Name, ?) > ?) OR " +
                "(MATCH(i.name, i.brand) AGAINST(? IN BOOLEAN MODE) OR " +
                "MATCH(c.Category_Name) AGAINST(? IN BOOLEAN MODE))) " +
                "ORDER BY GREATEST(name_similarity, brand_similarity, category_similarity) DESC " +
                "LIMIT 5;";
        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, prefix);
            pstmt.setString(2, prefix);
            pstmt.setString(3, prefix);
            pstmt.setString(4, prefix);
            pstmt.setString(5, prefix);
            pstmt.setString(6, prefix);
            pstmt.setString(7, prefix);
            pstmt.setInt(8, similarityThreshold);
            pstmt.setString(9, prefix);
            pstmt.setInt(10, similarityThreshold);
            pstmt.setString(11, prefix);
            pstmt.setInt(12, similarityThreshold);
            pstmt.setString(13, '+' + prefix + '*');
            pstmt.setString(14, '+' + prefix + '*');

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    if (rs.getInt("name_similarity") >= rs.getInt("brand_similarity") && rs.getInt("name_similarity") >= rs.getInt("category_similarity")) {
                        suggestions.add(rs.getString("i.name"));
                    } else if (rs.getInt("brand_similarity") >= rs.getInt("category_similarity")) {
                        suggestions.add(rs.getString("i.brand"));
                    } else {
                        suggestions.add(rs.getString("c.Category_Name"));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error during autocomplete search: " + e.getMessage());
            e.printStackTrace();
        }
        return suggestions;
    }









    private void getAndSetInfo(List<Search> searchResults, PreparedStatement pstmt) throws SQLException {
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Search search = new Search(
                        rs.getInt("Item_ID"),
                        rs.getString("brand"),
                        rs.getString("name"),
                        rs.getString("Category_Name"),
                        rs.getDouble("Current_Price"),
                        rs.getInt("Auction_ID"),
                        rs.getString("auction_status")
                );
                searchResults.add(search);
            }
        }
    }

    public List<Search> filterByFields(Map<String, String> filters) {
        List<Search> searchResults = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT i.Item_ID, i.brand, i.name, c.Category_Name, a.Current_Price, a.Auction_ID, a.auction_status " +
                "FROM Items i INNER JOIN Category c ON i.Category_ID = c.Category_ID " +
                "INNER JOIN Auction a ON i.Item_ID = a.Item_ID WHERE ");

        List<String> conditions = new ArrayList<>();
        List<String> parameters = new ArrayList<>();

        buildSqlConditions(filters, conditions, parameters);
        sql.append(String.join(" AND ", conditions));

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setString(i + 1, parameters.get(i));
            }
            getAndSetInfo(searchResults, pstmt);
        } catch (SQLException e) {
            System.out.println("Error during filter search: " + e.getMessage());
            e.printStackTrace();
        }
        return searchResults;
    }




    public List<Search> filterAndFuzzySearch(Map<String, String> filters, String fuzzyQuery) {
        List<Search> searchResults = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT i.Item_ID, i.brand, i.name, c.Category_Name, a.Current_Price, a.Auction_ID, a.auction_status, " +
                "GREATEST(LEVENSHTEIN_RATIO(i.name, ?), LEVENSHTEIN_RATIO(i.brand, ?), LEVENSHTEIN_RATIO(c.Category_Name, ?)) AS similarity " +
                "FROM Items i " +
                "INNER JOIN Category c ON i.Category_ID = c.Category_ID " +
                "INNER JOIN Auction a ON i.Item_ID = a.Item_ID WHERE ");

        sql.append("(MATCH(i.brand, i.name, c.Category_Name) AGAINST (? IN BOOLEAN MODE) OR " +
                "LEVENSHTEIN_RATIO(i.name, ?) > 30 OR " +
                "LEVENSHTEIN_RATIO(i.brand, ?) > 30 OR " +
                "LEVENSHTEIN_RATIO(c.Category_Name, ?) > 30) AND ");

        List<String> parameters = new ArrayList<>();
        parameters.add(fuzzyQuery);
        parameters.add(fuzzyQuery);
        parameters.add(fuzzyQuery);
        parameters.add(fuzzyQuery + "*");
        parameters.add(fuzzyQuery);
        parameters.add(fuzzyQuery);
        parameters.add(fuzzyQuery);

        List<String> conditions = new ArrayList<>();
        buildSqlConditions(filters, conditions, parameters);

        sql.append(String.join(" AND ", conditions));
        if (conditions.size() > 0) {
            sql.setLength(sql.length() - 5);
        }

        sql.append(" ORDER BY similarity DESC;");

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setString(i + 1, parameters.get(i));
            }
            getAndSetInfo(searchResults, pstmt);
        } catch (SQLException e) {
            System.out.println("Error during combined filter and fuzzy search: " + e.getMessage());
            e.printStackTrace();
        }
        return searchResults;
    }



    private void buildSqlConditions(Map<String, String> filters, List<String> conditions, List<String> parameters) {
        filters.forEach((key, value) -> {
            switch (key) {
                case "Category_name":
                    conditions.add("c.Category_Name LIKE ?");
                    parameters.add("%" + value + "%");
                    break;
                case "Sub_category_name":
                    conditions.add("c.Parent_Category_ID IN (SELECT Category_ID FROM Category WHERE Category_Name LIKE ?)");
                    parameters.add("%" + value + "%");
                    break;
                case "Item_name":
                    conditions.add("i.name LIKE ?");
                    parameters.add("%" + value + "%");
                    break;
                case "Item_brand":
                    conditions.add("i.brand LIKE ?");
                    parameters.add(value + "%");
                    break;
                case "price_range":
                    if ("low".equals(value.toLowerCase())) {
                        conditions.add("a.Current_Price BETWEEN 0 AND 49");
                    } else if ("medium".equals(value.toLowerCase())) {
                        conditions.add("a.Current_Price BETWEEN 50 AND 149");
                    } else if ("high".equals(value.toLowerCase())) {
                        conditions.add("a.Current_Price >= 150");
                    }
                    break;
                case "color_variants":
                    conditions.add("i.color_variants LIKE ?");
                    parameters.add("%" + value + "%");
                    break;
                case "auction_status":
                    conditions.add("a.auction_status = ?");
                    parameters.add(value);
                    break;
            }
        });
    }


}