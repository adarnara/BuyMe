package com.mybuy.controller;

import com.mybuy.model.AutoBidModel;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AlertAutoBidServlet", urlPatterns = {"/alertAutoBid"})
public class AlertAutoBidServlet extends HttpServlet {

    private AutoBidModel autoBidModel;

    @Override
    public void init() {
        autoBidModel = new AutoBidModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int auctionId = Integer.parseInt(request.getParameter("auctionId"));
        double upperLimit = Double.parseDouble(request.getParameter("upperLimit"));

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        boolean isExceeded = autoBidModel.isCurrentPriceExceedingLimit(auctionId, userId, upperLimit);
        if (isExceeded) {
            response.getWriter().write("Alert: A higher bid than your set upper limit has been placed by another user for auction ID " + auctionId);
        } else {
            response.getWriter().write("Your upper limit is still safe for auction ID " + auctionId);
        }
    }
}
