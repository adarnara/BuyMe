package com.mybuy.controller;

import com.mybuy.model.Auction;
import com.mybuy.model.Login;
import com.mybuy.model.LoginModel;
import com.mybuy.model.UserType;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private LoginModel loginModel;

    @Override
    public void init() {
        loginModel = new LoginModel();
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
                    List<Auction> auctions = loginModel.getAuctions(session.getAttribute("username").toString());
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
                List<Auction> auctions = loginModel.getAuctions(authenticatedUser.getUsername());
                request.setAttribute("auctions", auctions);
                request.getRequestDispatcher("/WEB-INF/view/welcome_page_seller.jsp").forward(request, response);
                break;
            case ADMIN:
            	request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
            	break;
            case CUSTOMER_REP:
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

        try {
            Date closingDate = dateFormat.parse(closingDateStr + " " + closingTimeStr);

            Auction newAuction = new Auction(
                    closingDate,
                    closingDate,
                    Double.parseDouble(request.getParameter("initialPrice")),
                    Double.parseDouble(request.getParameter("bidIncrement")),
                    Double.parseDouble(request.getParameter("minimumPrice")),
                    userId,
                    1 // TODO: update
            );

            int newAuctionID = loginModel.addAuction(newAuction);

            if (newAuctionID > 0) {
                response.sendRedirect(request.getContextPath() + "/auction/" + newAuctionID);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}