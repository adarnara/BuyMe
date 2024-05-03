package com.mybuy.controller;


import com.mybuy.model.QuestionResponse;
import com.mybuy.model.QuestionResponseModel;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "QuestionResponseServlet", urlPatterns = {"/response"})
public class QuestionResponseServlet extends HttpServlet{

	private QuestionResponseModel questionResponseModel;
	
	@Override
	public void init() throws ServletException {
		super.init();
		questionResponseModel = new QuestionResponseModel();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String rep_id = (String) session.getAttribute("rep_id");
		String id = request.getParameter("id");
		String ans = request.getParameter("answer");
		
		QuestionResponse q = new QuestionResponse(rep_id, ans, id);
		boolean j = questionResponseModel.questionResponse(q);
		
        String origin = request.getParameter("origin");
        request.getRequestDispatcher("/WEB-INF/view/" + origin + ".jsp").forward(request, response);
	}
}