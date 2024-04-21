package com.mybuy.controller;

import com.mybuy.model.Login;
import com.mybuy.model.LoginModel;
import com.mybuy.model.UserType;
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
        Login authenticatedUser = loginModel.authenticateUserLogin(loginForm.getUsernameOrEmail(), loginForm.getPassword());

        if (authenticatedUser == null) {
            request.setAttribute("loginMessage", "Invalid Credentials");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
        else {
            request.getSession().setAttribute("username", authenticatedUser.getUsername());
            
            if (authenticatedUser.getUserType() == UserType.ADMIN) {
            	request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
            } else if (authenticatedUser.getUserType() == UserType.CUSTOMER_REP) {
            	request.getRequestDispatcher("/WEB-INF/view/customerRep.jsp").forward(request, response);
            } else {
            	request.getRequestDispatcher("/WEB-INF/view/welcome.jsp").forward(request, response);
            }
        }
    }
}