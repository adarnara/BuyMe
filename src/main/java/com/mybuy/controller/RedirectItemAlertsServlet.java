package com.mybuy.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

@WebServlet(name = "ItemAlertsServlet", urlPatterns = {"/itemAlerts"})
public class RedirectItemAlertsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/view/item_alerts.jsp").forward(request, response);
    }
}
