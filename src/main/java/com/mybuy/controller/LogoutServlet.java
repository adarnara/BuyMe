package com.mybuy.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    private static final String logoutTrueFlag = "true";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logoutParam = request.getParameter("logout");
        if (logoutTrueFlag.equals(logoutParam)) {
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
}
