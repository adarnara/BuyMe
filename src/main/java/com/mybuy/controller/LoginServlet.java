package com.mybuy.controller;

import com.mybuy.model.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private LoginModel loginModel;
    private AuctionModel auctionModel;
    private ItemModel itemModel;

    @Override
    public void init() {
        loginModel = new LoginModel();
        auctionModel = new AuctionModel();
        itemModel = new ItemModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("username") != null) {
            if (session.getAttribute("username").equals("customer_rep")) {
                request.getRequestDispatcher("/WEB-INF/view/customerRep.jsp").forward(request, response);
            } else {
                String endUserType = loginModel.getEndUserType(session.getAttribute("username").toString());
                request.setAttribute("userType", endUserType);
                if(endUserType.equals("seller")) {
                    List<Auction> auctions = auctionModel.getAuctionsByUsername(session.getAttribute("username").toString());
                    request.setAttribute("auctions", auctions);
                    request.getRequestDispatcher("/WEB-INF/view/welcome_page_seller.jsp").forward(request, response);
                }
                request.getRequestDispatcher("/WEB-INF/view/welcome_page_buyer.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // To separate form modal submissions and login submissions
        String modalSubmit = request.getParameter("modalSubmit");

        if (modalSubmit != null && modalSubmit.equals("true")) {
            handleFormModalSubmission(request, response);
            return;
        }

        String usernameOrEmail = request.getParameter("usernameOrEmail");
        String password = request.getParameter("password");

        Login loginForm = new Login(usernameOrEmail, password);
        System.out.println("1");
        Login authenticatedUser = loginModel.authenticateUserLogin(loginForm.getUsernameOrEmail(), loginForm.getPassword());
        System.out.println("2");
        
        if (authenticatedUser == null) {
        	System.out.println("fucked");
            request.setAttribute("loginMessage", "Invalid Credentials");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
        else {
            request.getSession().setAttribute("username", authenticatedUser.getUsername());
            switch(authenticatedUser.getUserType()) {
            case BUYER:
                System.out.println("onward to Buyer page!");
                request.getRequestDispatcher("/WEB-INF/view/welcome_page_buyer.jsp").forward(request, response);
            	break;
            case SELLER:
                System.out.println("onward to Seller page!");
                List<Auction> auctions = auctionModel.getAuctionsByUsername(authenticatedUser.getUsername());
                request.setAttribute("auctions", auctions);
                request.getRequestDispatcher("/WEB-INF/view/welcome_page_seller.jsp").forward(request, response);
                break;
            case ADMIN:
            	request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
            	break;
            case CUSTOMER_REP:
            	request.getSession().setAttribute("rep_id", authenticatedUser.getUserID());
            	request.getRequestDispatcher("/WEB-INF/view/customerRep.jsp").forward(request, response);
            	break;
            default:
            	System.out.println("Weird shit happened here");
            	request.getRequestDispatcher("/index.jsp").forward(request, response);
            	break;
            }
        }
    }

    private void handleFormModalSubmission(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = loginModel.getUserId(request.getSession().getAttribute("username").toString());
        String closingDateStr = request.getParameter("closingDate");
        String closingTimeStr = request.getParameter("closingTime");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        double minimumPriceValue = 0;
        if(!request.getParameter("minimumPrice").isEmpty()) {
            minimumPriceValue = Double.parseDouble(request.getParameter("minimumPrice"));
        }

        String itemCategory = request.getParameter("itemCategory");
        String itemBrand = request.getParameter("itemBrand");
        String itemName = request.getParameter("itemName");
        String itemColor = request.getParameter("itemColor");

        try {
            Item newItem = itemModel.createNewItem(itemCategory, itemBrand, itemName, itemColor);
            if(newItem == null) {
                System.out.println("Error creating new item");
                return;
            }

            int itemId = itemModel.addItem(newItem);

            Date closingDate = dateFormat.parse(closingDateStr + " " + closingTimeStr);

            Auction newAuction = new Auction(
                    closingDate,
                    closingDate,
                    Double.parseDouble(request.getParameter("initialPrice")),
                    Double.parseDouble(request.getParameter("bidIncrement")),
                    minimumPriceValue,
                    userId,
                    itemId
            );

            int newAuctionID = auctionModel.addAuction(newAuction);

            if (newAuctionID > 0) {
                response.sendRedirect(request.getContextPath() + "/auction/" + newAuctionID);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}