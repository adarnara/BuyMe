package com.mybuy.controller;

import com.mybuy.model.HelloWorld;
import com.mybuy.model.HelloWorldModel;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    private HelloWorldModel helloWorldModel;

    @Override
    public void init() {
        this.helloWorldModel = new HelloWorldModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HelloWorld helloWorld = helloWorldModel.getHelloMessage();
        request.setAttribute("message", helloWorld.getMessage());
        request.getRequestDispatcher("/WEB-INF/view/hello.jsp").forward(request, response);
    }
}
