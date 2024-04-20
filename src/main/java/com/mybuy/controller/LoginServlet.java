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
        Login authenticatedUser = loginModel.authenticateUserLogin(loginForm.getUsernameOrEmail(), loginForm.getPassword());

        if (authenticatedUser == null) {
            request.setAttribute("loginMessage", "Invalid Credentials");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
        else {
            request.getSession().setAttribute("username", authenticatedUser.getUsername());

            if ("buyer".equals(authenticatedUser.getEndUserType())) {
                System.out.println("onward to Buyer page!");
                request.getRequestDispatcher("/WEB-INF/view/welcome_page_buyer.jsp").forward(request, response);
            }
            else if ("seller".equals(authenticatedUser.getEndUserType())) {
                System.out.println("onward to Seller page!");
                request.getRequestDispatcher("/WEB-INF/view/welcome_page_seller.jsp").forward(request, response);
            }
        }
    }
}