package com.mybuy.controller;

import com.mybuy.model.Auction;
import com.mybuy.dao.AuctionDAO;
import com.mybuy.model.AuctionModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AuctionServlet", urlPatterns = {"/auction/*"})
public class AuctionServlet extends HttpServlet {
    private AuctionModel auctionModel;

    @Override
    public void init() throws ServletException{
        auctionModel = new AuctionModel();
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

                Auction auction = auctionModel.getAuction(auctionId);

                req.setAttribute("auction", auction);

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
