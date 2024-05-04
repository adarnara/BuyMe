package com.mybuy.controller;

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

    @Override
    public void init() throws ServletException {
        alertModel = new AlertModel();
    }

}
