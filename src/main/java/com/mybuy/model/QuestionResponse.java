package com.mybuy.model;

public class QuestionResponse {
	private String repId;
	private String answer;
	private String questionId;
	
	public QuestionResponse(String repId, String answer, String questionId) {
		this.setRepId(repId);
		this.setAnswer(answer);
		this.setQuestionId(questionId);
	}

	public String getRepId() {
		return repId;
	}

	public void setRepId(String repId) {
		this.repId = repId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
}
