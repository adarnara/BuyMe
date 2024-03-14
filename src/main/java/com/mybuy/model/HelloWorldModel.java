package com.mybuy.model;

import com.mybuy.dao.HelloWorldDAO;
import com.mybuy.dao.IHelloWorldDAO;

// Encapsulates the business logic and interacts with the DAO Interface.
public class HelloWorldModel {

    private IHelloWorldDAO helloWorldDAO;

    public HelloWorldModel() {
        this.helloWorldDAO = new HelloWorldDAO();
    }

    public HelloWorld getHelloMessage() {
        return helloWorldDAO.getHelloMessage();
    }
}
