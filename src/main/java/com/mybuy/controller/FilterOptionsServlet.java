package com.mybuy.controller;

import com.mybuy.model.FilterModel;
import com.mybuy.model.FilterOption;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "FilterOptionsServlet", urlPatterns = {"/filter-options"})
public class FilterOptionsServlet extends HttpServlet {

    private FilterModel filterModel;

    @Override
    public void init() {
        filterModel = new FilterModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String optionType = request.getParameter("optionType");
        try {
            List<FilterOption> options = filterModel.getFilterOptions(optionType);
            for (FilterOption option : options) {
                response.getWriter().println(option.getName());
            }
        } catch (Exception e) {
            response.getWriter().println("Error: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}