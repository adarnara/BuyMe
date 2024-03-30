package com.mybuy.controller;

import com.mybuy.model.Login;
import com.mybuy.model.LoginModel;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private LoginModel loginModel;

    @Override
    public void init() {
        loginModel = new LoginModel();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usernameOrEmail = request.getParameter("usernameOrEmail");
        String password = request.getParameter("password");

        Login loginForm = new Login(usernameOrEmail, password);
        boolean success = loginModel.authenticateUser(loginForm.getUsernameOrEmail(), loginForm.getPassword());

        if (!success) {
            request.setAttribute("loginMessage", "Invalid Credentials");
        } else {
            request.setAttribute("loginMessage", "Success");
        }
        request.getRequestDispatcher("/WEB-INF/view/loginAndRegister.jsp").forward(request, response);
    }
}
