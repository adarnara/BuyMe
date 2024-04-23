package com.mybuy.controller;

import com.mybuy.model.Auction;
import com.mybuy.dao.AuctionDAO;
import com.mybuy.model.AuctionModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AuctionServlet", urlPatterns = {"/auction/*"})
public class AuctionServlet extends HttpServlet {
    private AuctionDAO auctionDAO;
    private AuctionModel auctionModel;

    @Override
    public void init() throws ServletException{
        auctionModel = new AuctionModel();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] pathParts = req.getPathInfo().split("/");
        if (pathParts.length == 2) {
            int auctionId = Integer.parseInt(pathParts[1]);

            Auction auction = auctionModel.getAuction(auctionId);

            req.setAttribute("auction", auction);

            req.getRequestDispatcher("/WEB-INF/view/auction_page.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Auction ID not provided");
        }
    }
}