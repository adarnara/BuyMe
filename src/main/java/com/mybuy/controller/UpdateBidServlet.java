package com.mybuy.controller;

import com.mybuy.model.BidModel;
import com.mybuy.model.Bid;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateBidServlet", urlPatterns = {"/updateBid"})
public class UpdateBidServlet extends HttpServlet {

    private BidModel bidModel;

    @Override
    public void init() {
        bidModel = new BidModel();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        if (!method.equals("PATCH")) {
            super.service(req, resp);
        }

        this.doPatch(req, resp);
    }


    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            System.out.println("Received bidId: " + request.getParameter("bidId"));
            System.out.println("Received newBidAmount: " + request.getParameter("newBidAmount"));

            int bidId = Integer.parseInt(request.getParameter("bidId"));
            double newBidAmount = Double.parseDouble(request.getParameter("newBidAmount"));

            boolean bidUpdated = bidModel.updateBid(bidId, newBidAmount);

            if (bidUpdated) {
                response.getWriter().println("Bid updated successfully.");
            } else {
                response.getWriter().println("Unable to update bid.");
            }
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid bid data: " + e.getMessage());
        } catch (Exception e) {
            response.getWriter().println("An error occurred: " + e.getMessage());
        }
    }
}
