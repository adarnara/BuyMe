package com.mybuy.model;

import com.mybuy.dao.IAskQuestionDAO;
import com.mybuy.dao.AskQuestionDAO;

public class AskQuestionModel {
	
	private IAskQuestionDAO askQuestionDAO;
	
	public AskQuestionModel() {
		this.askQuestionDAO = new AskQuestionDAO();
	}
	
	public boolean askQuestion(Question question) {
		return askQuestionDAO.askQuestion(question);
	}
}