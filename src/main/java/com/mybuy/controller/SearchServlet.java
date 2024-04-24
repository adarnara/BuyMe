package com.mybuy.controller;

import com.mybuy.model.Search;
import com.mybuy.model.SearchModel;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {

    private SearchModel searchModel;

    @Override
    public void init() {
        searchModel = new SearchModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        try {
            List<Search> searchResults;
            String query = request.getParameter("query");
            Map<String, String> filters = new HashMap<>();
            String[] params = {"Category_name", "Sub_category_name", "Item_name", "Item_brand", "price_range", "color_variants", "auction_status"};
            for (String param : params) {
                if (request.getParameter(param) != null) {
                    filters.put(param, request.getParameter(param));
                }
            }

            if (query != null && !filters.isEmpty()) {
                searchResults = searchModel.filterAndFuzzySearchItems(filters, query);
            } else if (!filters.isEmpty()) {
                searchResults = searchModel.filterItems(filters);
            } else {
                searchResults = searchModel.searchItems(query);
            }

            for (Search searchResult : searchResults) {
                response.getWriter().println(
                        "Item ID: " + searchResult.getItemId() +
                                ", Brand: " + searchResult.getBrand() +
                                ", Name: " + searchResult.getName() +
                                ", Category: " + searchResult.getCategoryName() +
                                ", Current Price: $" + searchResult.getCurrentPrice() +
                                ", Auction ID: " + searchResult.getAuctionId() +
                                ", Auction Status: " + searchResult.getAuctionStatus()
                );
            }
        } catch (Exception e) {
            response.getWriter().println("Error in search: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
