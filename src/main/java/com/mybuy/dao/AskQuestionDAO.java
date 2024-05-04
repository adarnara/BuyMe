package com.mybuy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mybuy.model.Question;
import com.mybuy.utils.ApplicationDB;

public class AskQuestionDAO implements IAskQuestionDAO {

	@Override
	public boolean askQuestion(Question question) {
		String sql = "INSERT INTO Question (question_text, User_ID) VALUES ? ?";
		try (Connection conn = ApplicationDB.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1,  question.getQuestion());
			pstmt.setString(2,  question.getUserID());
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}
}