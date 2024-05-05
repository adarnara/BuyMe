package com.mybuy.controller;

import com.mybuy.model.RandomItemModel;
import com.mybuy.model.Search;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "RandomItemServlet", urlPatterns = {"/random-items"})
public class RandomItemServlet extends HttpServlet {

    private RandomItemModel randomItemModel;

    @Override
    public void init() {
        randomItemModel = new RandomItemModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        try {
            List<Search> randomItems = randomItemModel.getRandomItems();
            for (Search item : randomItems) {
                response.getWriter().println(
                        "Item ID: " + item.getItemId() +
                                ", Brand: " + item.getBrand() +
                                ", Name: " + item.getName() +
                                ", Category: " + item.getCategoryName() +
                                ", Current Price: $" + item.getCurrentPrice() +
                                ", Auction ID: " + item.getAuctionId() +
                                ", Auction Status: " + item.getAuctionStatus()
                );
            }
        } catch (Exception e) {
            response.getWriter().println("Error fetching random items: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
