package com.mybuy.model;

public class SearchQuestionAndAnswer {
    private int questionId;
    private String questionText;
    private String answerText;
    private int customerRepId;
    private int userId;

    public int getQuestionId() { return questionId; }
    public void setQuestionId(int questionId) { this.questionId = questionId; }
    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }
    public String getAnswerText() { return answerText; }
    public void setAnswerText(String answerText) { this.answerText = answerText; }
    public int getCustomerRepId() { return customerRepId; }
    public void setCustomerRepId(int customerRepId) { this.customerRepId = customerRepId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}
