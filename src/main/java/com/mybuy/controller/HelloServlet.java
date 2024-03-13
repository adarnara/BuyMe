package com.mybuy.controller;

import com.mybuy.model.HelloWorld;
import com.mybuy.dao.IHelloWorldDAO;
import com.mybuy.dao.DaoFactory;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IHelloWorldDAO dao = DaoFactory.getHelloWorldDao();
        HelloWorld message = dao.getHelloMessage();
        request.setAttribute("message", message.getMessage());
        request.getRequestDispatcher("/WEB-INF/view/hello.jsp").forward(request, response);
    }
}

