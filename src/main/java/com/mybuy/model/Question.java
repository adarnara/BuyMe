package com.mybuy.model;

public class Question {
	private String question;
	private String userID;
	
	public Question(String question, String userID) {
		this.setQuestion(question);
		this.setUserID(userID);
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
}
