package com.mybuy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mybuy.model.QuestionResponse;
import com.mybuy.utils.ApplicationDB;

public class QuestionResponseDAO implements IQuestionResponseDAO {

	@Override
	public boolean question(QuestionResponse q) {
		String sql = "UPDATE Question SET answer_text = ?, CustomerRep_ID = ? WHERE question_ID = ?";
		try (Connection conn = ApplicationDB.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, q.getAnswer());
			pstmt.setString(2, q.getRepId());
			pstmt.setString(3, q.getQuestionId());
			int rowsAffected = pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}
	
}
