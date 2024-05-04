package com.mybuy.controller;

import com.mybuy.model.AskQuestionModel;
import com.mybuy.model.Question;
import com.mybuy.model.LoginModel;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "AskQuestionServlet", urlPatterns = {"/question"})
public class AskQuestionServlet extends HttpServlet {

	private AskQuestionModel askQuestionModel;
	private LoginModel loginModel;
	
	@Override
	public void init() throws ServletException {
		super.init();
		askQuestionModel = new AskQuestionModel();
		loginModel = new LoginModel();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
		String question = request.getParameter("question");
		int userId = loginModel.getUserId(username);
		
		Question q = new Question(question, String.valueOf(userId));
		askQuestionModel.askQuestion(q);
		
        String origin = request.getParameter("origin");
        request.getRequestDispatcher("/WEB-INF/view/" + origin + ".jsp").forward(request, response);
	}


}