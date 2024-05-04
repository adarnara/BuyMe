package com.mybuy.controller;

import com.mybuy.model.AuctionWinnerModel;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AuctionWinnerAlertServlet", urlPatterns = {"/auctionWinnerAlert"})
public class AuctionWinnerAlertServlet extends HttpServlet {
    private AuctionWinnerModel auctionWinnerModel;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        auctionWinnerModel = new AuctionWinnerModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int auctionId = auctionWinnerModel.getAuctionIdIfUserWon(userId);
        if (auctionId != -1) {
            response.getWriter().print("Alert, you have won Auction ID: " + auctionId);
        } else {
            response.getWriter().print("You have not won any auctions.");
        }
    }

    @Override
    public void destroy() {
        auctionWinnerModel.shutdownScheduler();
    }
}
