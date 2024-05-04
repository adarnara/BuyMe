package com.mybuy.model;

import com.mybuy.dao.ISearchQuestionAndAnswerDAO;
import com.mybuy.dao.SearchQuestionAndAnswerDAO;

public class SearchQuestionAndAnswerModel {
    private ISearchQuestionAndAnswerDAO searchDAO;

    public SearchQuestionAndAnswerModel() {
        searchDAO = new SearchQuestionAndAnswerDAO();
    }

    public String searchQuestionsByKeywords(String query) {
        StringBuilder sb = new StringBuilder();
        searchDAO.searchByKeywords(query).forEach(qna -> {
            sb.append("Question: ").append(qna.getQuestionText()).append("\n");
            if (qna.getAnswerText() != null && !qna.getAnswerText().isEmpty()) {
                sb.append("Answer: ").append(qna.getAnswerText()).append("\n\n");
            }
        });
        return sb.toString();
    }
}
