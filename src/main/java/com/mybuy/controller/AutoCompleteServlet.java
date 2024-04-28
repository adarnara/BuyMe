package com.mybuy.controller;

import com.mybuy.model.SearchModel;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AutoCompleteServlet", urlPatterns = {"/autocomplete"})
public class AutoCompleteServlet extends HttpServlet {

    private SearchModel searchModel;

    @Override
    public void init() {
        searchModel = new SearchModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String prefix = request.getParameter("prefix");
            List<String> suggestions = searchModel.autocompleteItems(prefix);
            response.getWriter().println(suggestions);
        } catch (Exception e) {
            response.getWriter().println("Error in autocomplete: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
