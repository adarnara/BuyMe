package com.mybuy.controller;

import com.mybuy.model.Search;
import com.mybuy.model.SearchModel;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {

    private SearchModel searchModel;

    @Override
    public void init() {
        searchModel = new SearchModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String query = request.getParameter("query");
            List<Search> searchResults = searchModel.searchItems(query);

            for (Search searchResult : searchResults) {
                response.getWriter().println(
                        "Item ID: " + searchResult.getItemId() +
                                ", Brand: " + searchResult.getBrand() +
                                ", Name: " + searchResult.getName() +
                                ", Category: " + searchResult.getCategoryName()
                );
            }
        } catch (Exception e) {
            response.getWriter().println("Error in search: " + e.getMessage());
        }
    }
}
