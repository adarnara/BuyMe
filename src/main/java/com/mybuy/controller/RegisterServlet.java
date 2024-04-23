package com.mybuy.controller;

import com.mybuy.model.Register;
import com.mybuy.model.RegisterModel;
import com.mybuy.model.UserType;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    private RegisterModel registerModel;

    @Override
    public void init() throws ServletException {
        super.init();
        registerModel = new RegisterModel();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");

        Register newUser = new Register(username, email, password, UserType.fromString(userType));

        boolean success = registerModel.insertUser(newUser);

        if (!success) {
            request.getSession().setAttribute("registrationMessage", "Registration Invalid");
        }
        else {
            request.getSession().setAttribute("registrationMessage", "Registration Success");
        }
        response.sendRedirect("index.jsp");
    }
}