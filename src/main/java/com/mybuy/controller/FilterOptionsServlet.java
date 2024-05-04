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
        response.setContentType("text/plain");  // Set content type as plain text
        response.setCharacterEncoding("UTF-8");

        try {
            List<FilterOption> options = filterModel.getFilterOptions(optionType);
            if (options != null && !options.isEmpty()) {
                for (FilterOption option : options) {
                    response.getWriter().println(option.getName());
                }
            } else {
                response.getWriter().println("No options found for " + optionType);
            }
        } catch (Exception e) {
            e.printStackTrace(response.getWriter());  // Print stack trace to response for debugging
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
