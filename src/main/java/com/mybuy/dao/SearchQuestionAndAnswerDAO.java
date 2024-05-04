package com.mybuy.dao;

import com.mybuy.model.SearchQuestionAndAnswer;
import com.mybuy.utils.ApplicationDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchQuestionAndAnswerDAO implements ISearchQuestionAndAnswerDAO {
    @Override
    public List<SearchQuestionAndAnswer> searchByKeywords(String query) {
        List<SearchQuestionAndAnswer> results = new ArrayList<>();
        String sql = "SELECT question_text, answer_text FROM Question WHERE MATCH(question_text, answer_text) AGAINST (? WITH QUERY EXPANSION) " +
                "UNION ALL " +
                "SELECT question_text, answer_text FROM Question WHERE (question_text REGEXP CONCAT('(?i)', ?) OR answer_text REGEXP CONCAT('(?i)', ?)) " +
                "AND NOT EXISTS (" +
                "SELECT 1 FROM Question WHERE MATCH(question_text, answer_text) AGAINST (? WITH QUERY EXPANSION))";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, query);
            pstmt.setString(2, query);
            pstmt.setString(3, query);
            pstmt.setString(4, query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                SearchQuestionAndAnswer qna = new SearchQuestionAndAnswer();
                qna.setQuestionText(rs.getString("question_text"));
                qna.setAnswerText(rs.getString("answer_text"));
                results.add(qna);
            }
        } catch (SQLException e) {
            System.err.println("Error searching questions: " + e.getMessage());
        }
        return results;
    }
}
