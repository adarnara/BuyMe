package com.mybuy.controller;

import com.mybuy.model.*;
import com.mybuy.dao.AuctionDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AuctionServlet", urlPatterns = {"/auction/*"})
public class AuctionServlet extends HttpServlet {
    private AuctionModel auctionModel;
    private BidModel bidModel;

    @Override
    public void init() throws ServletException{
        auctionModel = new AuctionModel();
        bidModel = new BidModel();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Check if the user is authenticated
        HttpSession session = req.getSession(false); // Don't create a new session if it doesn't exist
        if (session != null && session.getAttribute("username") != null) {
            // User is authenticated, allow access to the requested resource
            String[] pathParts = req.getPathInfo().split("/");
            if (pathParts.length == 2) {
                int auctionId = Integer.parseInt(pathParts[1]);

                Auction auction = auctionModel.getAuctionById(auctionId);
                List<Bid> bids = bidModel.getBidsByAuctionId(auctionId);
                List<Auction> similarAuctions = auctionModel.getSimilarAuctions(auction.getItemId());

                req.setAttribute("auction", auction);
                req.setAttribute("bids", bids);
                req.setAttribute("similarAuctions", similarAuctions);

                req.getRequestDispatcher("/WEB-INF/view/auction_page.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Auction ID not provided");
            }
        } else {
            // User is not authenticated, redirect to the login page
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
