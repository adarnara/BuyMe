package com.mybuy.model;

import com.mybuy.dao.IQuestionResponseDAO;
import com.mybuy.dao.QuestionResponseDAO;

public class QuestionResponseModel {
	
	private IQuestionResponseDAO questionResponseDAO;
	
	public QuestionResponseModel() {
		this.questionResponseDAO = new QuestionResponseDAO();
	}
	
	public boolean questionResponse(QuestionResponse response) {
		return questionResponseDAO.question(response);
	}
}
