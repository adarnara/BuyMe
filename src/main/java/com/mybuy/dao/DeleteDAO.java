package com.mybuy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mybuy.model.Delete;
import com.mybuy.utils.ApplicationDB;
import com.mybuy.model.UserType;

public class DeleteDAO implements IDeleteDAO {
	public boolean delete(Delete user) {
		String id;
		if (user.getType() == UserType.END_USER) {
			id = "User_id";
		} else {
			id = "CustomerRep_id";
		}
		String sql = "DELETE FROM " + user.getType().toName() + " WHERE " + id + " = " + user.getId();
		System.out.println(sql);
		System.out.println("Here4");
        try (Connection conn = ApplicationDB.getConnection();
            	PreparedStatement pstmt = conn.prepareStatement(sql)) {
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                System.out.println("Couldn't delete user");
                return false;
            }
	}
}
