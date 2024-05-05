package com.mybuy.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mybuy.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AlertServlet", urlPatterns = {"/alert"})
public class AlertServlet extends HttpServlet {
    private AlertModel alertModel;
    private LoginModel loginModel;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        alertModel = new AlertModel();
        loginModel = new LoginModel();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = loginModel.getUserId((String) request.getSession().getAttribute("username"));
        Alert alert = alertModel.newAlertById(userId);

        JsonObject json = new JsonObject();
        String jsonAlert = json.toString();
        if(alert != null) {
            jsonAlert = gson.toJson(alert);
        }

        // Send the alert message as JSON response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonAlert);
    }
}
