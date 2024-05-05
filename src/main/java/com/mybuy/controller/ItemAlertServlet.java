package com.mybuy.controller;

import com.mybuy.model.ItemAlert;
import com.mybuy.model.ItemAlertModel;
import com.mybuy.model.LoginModel;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ItemAlertServlet", urlPatterns = {"/setItemAlert"})
public class ItemAlertServlet extends HttpServlet {

    private ItemAlertModel itemAlertModel;
    private LoginModel loginModel;

    @Override
    public void init() {
        itemAlertModel = new ItemAlertModel();
        loginModel = new LoginModel();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = loginModel.getUserId(request.getSession().getAttribute("username").toString());
        String itemName = request.getParameter("itemName");
        String itemBrand = request.getParameter("itemBrand");
        String categoryName = request.getParameter("categoryName");
        String colorVariant = request.getParameter("colorVariant");

        ItemAlert itemAlert = new ItemAlert(userId, null, "Pending", null, itemName, itemBrand, categoryName, colorVariant);
        boolean result = itemAlertModel.setItemAlert(itemAlert);

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("Result: " + result);
    }
}