package com.mybuy.controller;

import com.mybuy.model.BidModel;
import com.mybuy.model.Bid;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PlaceBidServlet", urlPatterns = {"/placeBid"})
public class PlaceBidServlet extends HttpServlet {

    private BidModel bidModel;

    @Override
    public void init() {
        bidModel = new BidModel();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            int auctionId = Integer.parseInt(request.getParameter("auctionId"));
            double bidAmount = Double.parseDouble(request.getParameter("bidAmount"));

            Bid bid = new Bid(userId, auctionId, bidAmount);
            String result = bidModel.placeBid(bid);

            response.getWriter().println(result);
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid bid data: " + e.getMessage());
        } catch (NullPointerException e) {
            response.getWriter().println("Missing parameter: " + e.getMessage());
        } catch (Exception e) {
            response.getWriter().println("An error occurred: " + e.getMessage());
        }
    }
}