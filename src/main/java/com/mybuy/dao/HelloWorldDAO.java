package com.mybuy.dao;

import com.mybuy.ApplicationDB;
import com.mybuy.model.HelloWorld;
import java.sql.*;

public class HelloWorldDAO implements IHelloWorldDAO {
    @Override
    public HelloWorld getHelloMessage() {
        try (Connection conn = ApplicationDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, helloworld FROM helloworld WHERE id = 1")) {

            if (rs.next()) {
                int id = rs.getInt("id");
                String message = rs.getString("helloworld");
                return new HelloWorld(id, message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
