package com.mybuy.dao;

import com.mybuy.ApplicationDB;
import com.mybuy.model.HelloWorld;
import java.sql.*;

public class HelloWorldDAO implements IHelloWorldDAO {
    @Override
    public HelloWorld getHelloMessage() {
        try (Connection conn = ApplicationDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet res = stmt.executeQuery("SELECT id, helloworld FROM helloworld WHERE id = 1")) {

            if (res.next()) {
                int id = res.getInt("id");
                String message = res.getString("helloworld");
                return new HelloWorld(id, message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
