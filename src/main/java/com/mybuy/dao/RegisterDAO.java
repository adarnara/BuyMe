package com.mybuy.dao;

import com.mybuy.utils.ApplicationDB;
import com.mybuy.model.Register;
import java.sql.*;

public class RegisterDAO implements IRegisterDAO {

    @Override
    public boolean insertUser(Register register) {
    	
        String sql = "INSERT INTO " + register.getType().toName() + " (" + register.getType().toName() + "_login, email_address, password, salt) VALUES (?, ?, ?, ?)";
        try (Connection conn = ApplicationDB.getConnection(); // Adarsh: Could later look into changing this to be object pooling or lazy instantiation
        	PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, register.getUsername());
            pstmt.setString(2, register.getEmail());
            pstmt.setString(3, register.getPassword());
            pstmt.setString(4, register.getSalt());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            // Adarsh: could do a logging statement here for now doing system.out.println
            System.out.println("Couldn't insert registration information");
            return false;
        }
    }
}