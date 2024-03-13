package com.mybuy.dao;


public class DaoFactory {
    private static IHelloWorldDAO helloWorldDaoInstance;

    public static IHelloWorldDAO getHelloWorldDao() {
        if (helloWorldDaoInstance == null) {
            helloWorldDaoInstance = new HelloWorldDAO();
        }
        return helloWorldDaoInstance;
    }
}
