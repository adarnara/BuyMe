package com.mybuy.model;

import com.mybuy.dao.IHelloWorldDAO;
import com.mybuy.dao.HelloWorldDAO;

public class HelloWorldModel {

    private IHelloWorldDAO helloWorldDAO;

    public HelloWorldModel() {
        this.helloWorldDAO = new HelloWorldDAO();
    }

    public HelloWorld getHelloMessage() {
        return helloWorldDAO.getHelloMessage();
    }
}
