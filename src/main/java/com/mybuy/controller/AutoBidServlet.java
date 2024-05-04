package com.mybuy.controller;

import com.mybuy.model.AutoBid;
import com.mybuy.model.AutoBidModel;
import com.mybuy.model.LoginModel;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AutoBidServlet", urlPatterns = {"/autoBid"})
public class AutoBidServlet extends HttpServlet {

    private AutoBidModel autoBidModel;
    private LoginModel loginModel;

    @Override
    public void init() {
        autoBidModel = new AutoBidModel();
        loginModel = new LoginModel();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int userId = loginModel.getUserId((String) request.getSession().getAttribute("username"));
            int auctionId = Integer.parseInt(request.getParameter("auctionId"));
            double maxAutoBidAmount = Double.parseDouble(request.getParameter("maxBidAmount"));
            double userBidIncrement = Double.parseDouble(request.getParameter("bidIncrement"));

            AutoBid autoBid = new AutoBid(userId, auctionId, maxAutoBidAmount, userBidIncrement);
            if(autoBidModel.processAutoBid(autoBid)) {
                response.sendRedirect(request.getContextPath() + "/auction/" + auctionId);
            }
        } catch (NumberFormatException | NullPointerException e) {
            response.getWriter().println("Error processing AutoBid: " + e.getMessage());
        }
    }
}