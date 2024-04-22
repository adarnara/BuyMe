package com.mybuy.controller;

import com.mybuy.model.Auction;
import com.mybuy.dao.AuctionDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AuctionServlet", urlPatterns = {"/auction/*"})
public class AuctionServlet extends HttpServlet {
    private AuctionDAO auctionDAO;

    @Override
    public void init() throws ServletException{
        auctionDAO = new AuctionDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] pathParts = req.getPathInfo().split("/");
        if (pathParts.length == 2) {
            int auctionId = Integer.parseInt(pathParts[1]);

            // Use auctionId to retrieve the corresponding auction data
            // For now, let's assume we have a method to retrieve auction data by ID
            Auction auction = auctionDAO.getAuctionById(auctionId);

            // Set auction object as an attribute to be used in the JSP
            req.setAttribute("auction", auction);

            // Forward the request to the JSP for rendering
            req.getRequestDispatcher("/WEB-INF/view/auction_page.jsp").forward(req, resp);
        } else {
            // Handle invalid URL (auction ID not provided)
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Auction ID not provided");
        }
    }
}
