package com.mybuy.controller;

import com.mybuy.model.Alert;
import com.mybuy.model.AlertModel;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AlertServlet", urlPatterns = {"/alert"})
public class AlertServlet extends HttpServlet {
    private AlertModel alertModel = new AlertModel();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        List<Alert> alerts = alertModel.runAlertCheck(userId);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        for (Alert alert : alerts) {
            response.getWriter().write(String.format("Alert! User %d has a new maximum bid of %.2f for auction ID %d.\n", alert.getUserId(), alert.getHighestBid(), alert.getAuctionId()));
        }
    }

    @Override
    public void destroy() {
        alertModel.shutdownScheduler();
    }
}
