package com.mybuy.dao;

import com.mybuy.model.SearchQuestionAndAnswer;
import java.util.List;

public interface ISearchQuestionAndAnswerDAO {
    List<SearchQuestionAndAnswer> searchByKeywords(String query);
}
