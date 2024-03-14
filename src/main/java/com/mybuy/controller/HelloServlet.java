package com.mybuy.controller;

import com.mybuy.model.HelloWorld;
import com.mybuy.model.HelloWorldModel;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class HelloServlet extends HttpServlet {
    private HelloWorldModel helloWorldModel;

    @Override
    public void init() throws ServletException {
        this.helloWorldModel = new HelloWorldModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HelloWorld helloWorld = helloWorldModel.getHelloMessage();
        request.setAttribute("message", helloWorld.getMessage());
        request.getRequestDispatcher("/WEB-INF/view/hello.jsp").forward(request, response);
    }
}
