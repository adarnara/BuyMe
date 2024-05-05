package com.mybuy.controller;

import com.mybuy.model.AlertModel;
import com.mybuy.model.Auction;
import com.mybuy.model.AuctionWinner;
import com.mybuy.model.AuctionWinnerModel;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebServlet(name = "AuctionWinnerServlet", urlPatterns = {"/auctionWinner"})
public class AuctionWinnerServlet extends HttpServlet {
    private ScheduledExecutorService scheduler;
    private AuctionWinnerModel auctionWinnerModel;
    private AlertModel alertModel;

    @Override
    public void init(ServletConfig config) throws ServletException {
        auctionWinnerModel = new AuctionWinnerModel();
        alertModel = new AlertModel();
        super.init(config);
        scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    public void startTimer() {
        // Schedule the run() method to run every X seconds
        scheduler.scheduleAtFixedRate(this::run, 0, 10, TimeUnit.SECONDS);
    }

    @Override
    public void destroy() {
        // Shutdown the scheduler when the servlet is destroyed
        scheduler.shutdown();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        startTimer();
    }

    private synchronized void run() {
        Auction auction = auctionWinnerModel.getEndedAuction();
        if(auction != null) {
            AuctionWinner auctionWinner = auctionWinnerModel.doesAuctionHaveWinner(auction);

            if(auctionWinner != null) {
                auctionWinnerModel.updateAuctionDetails(auctionWinner);
                alertModel.auctionWinnerAlert(auctionWinner);
            }

            // TODO: alert for seller for closed auction
            // TODO: alert for no highest bid or bid above reserve
        }
    }
}
