package com.mybuy.controller;

import com.mybuy.model.AutoBid;
import com.mybuy.model.AutoBidModel;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AutoBidServlet", urlPatterns = {"/autoBid"})
public class AutoBidServlet extends HttpServlet {

    private AutoBidModel autoBidModel;

    @Override
    public void init() {
        autoBidModel = new AutoBidModel();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            int auctionId = Integer.parseInt(request.getParameter("auctionId"));
            double maxAutoBidAmount = Double.parseDouble(request.getParameter("maxAutoBidAmount"));
            Double userBidIncrement = request.getParameter("bidIncrement") != null ? Double.parseDouble(request.getParameter("bidIncrement")) : null;

            AutoBid autoBid = new AutoBid(userId, auctionId, maxAutoBidAmount, userBidIncrement);
            boolean result = autoBidModel.processAutoBid(autoBid);

            response.getWriter().println(result ? "AutoBid process initiated successfully." : "Failed to place AutoBid.");
        } catch (NumberFormatException | NullPointerException e) {
            response.getWriter().println("Error processing AutoBid: " + e.getMessage());
        }
    }
}