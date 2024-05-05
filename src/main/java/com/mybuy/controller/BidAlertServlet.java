package com.mybuy.controller;

import com.google.gson.Gson;
import com.mybuy.model.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "BidAlertServlet", urlPatterns = {"/bidAlert"})
public class BidAlertServlet extends HttpServlet {
    private AlertModel alertModel;
    private Gson gson;

    @Override
    public void init() {
        alertModel = new AlertModel();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        List<Alert> alerts = alertModel.getBidAlertsForUser(userId);

        String jsonAlerts = gson.toJson(alerts);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonAlerts);
    }
}
