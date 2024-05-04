package com.mybuy.controller;

import com.mybuy.model.SearchQuestionAndAnswerModel;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SearchQuestionAndAnswerServlet", urlPatterns = {"/searchQuestions"})
public class SearchQuestionAndAnswerServlet extends HttpServlet {

    private SearchQuestionAndAnswerModel searchModel;

    @Override
    public void init() {
        searchModel = new SearchQuestionAndAnswerModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String query = request.getParameter("query");
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        String results = searchModel.searchQuestionsByKeywords(query);
        response.getWriter().write(results);
    }
}
