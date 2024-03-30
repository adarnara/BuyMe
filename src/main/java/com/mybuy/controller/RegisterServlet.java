package com.mybuy.controller;

import com.mybuy.model.Register;
import com.mybuy.model.RegisterModel;
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

        Register newUser = new Register(username, email, password);
        boolean success = registerModel.insertUser(newUser);

        if (!success) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Couldn't register user. Please try again.");
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Registration successful");
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
}
