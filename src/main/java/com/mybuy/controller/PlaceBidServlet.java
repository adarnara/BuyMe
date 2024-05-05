package com.mybuy.controller;

import com.mybuy.model.AlertModel;
import com.mybuy.model.BidModel;
import com.mybuy.model.Bid;
import com.mybuy.model.LoginModel;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PlaceBidServlet", urlPatterns = {"/placeBid"})
public class PlaceBidServlet extends HttpServlet {

    private BidModel bidModel;
    private LoginModel loginModel;
    private AlertModel alertModel;

    @Override
    public void init() {
        bidModel = new BidModel();
        loginModel = new LoginModel();
        alertModel = new AlertModel();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int userId = loginModel.getUserId((String) request.getSession().getAttribute("username"));
            int auctionId = Integer.parseInt(request.getParameter("auctionId"));
            double bidAmount = Double.parseDouble(request.getParameter("bidAmount"));

            Bid bid = new Bid(userId, auctionId, bidAmount);
            if(bidModel.placeBid(bid)) {
                alertModel.postBidAlert(auctionId, "A higher bid has been placed on Auction ID " + auctionId + ".", userId);
                response.sendRedirect(request.getContextPath() + "/auction/" + auctionId);
            }
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid bid data: " + e.getMessage());
        } catch (NullPointerException e) {
            response.getWriter().println("Missing parameter: " + e.getMessage());
        } catch (Exception e) {
            response.getWriter().println("An error occurred: " + e.getMessage());
        }
    }
}