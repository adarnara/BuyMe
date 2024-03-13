package com.mybuy.dao;

import com.mybuy.ApplicationDB;
import com.mybuy.model.HelloWorld;
import java.sql.*;

public class HelloWorldDAO implements IHelloWorldDAO {
    @Override
    public HelloWorld getHelloMessage() {
        HelloWorld helloWorld = new HelloWorld();
        try (Connection conn = ApplicationDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet res = stmt.executeQuery("SELECT id, helloworld FROM helloworld WHERE id = 1")) {

            if (res.next()) {
                helloWorld.setId(res.getInt("id"));
                helloWorld.setMessage(res.getString("helloworld"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return helloWorld;
    }
}
