package com.mybuy.dao;

import com.mybuy.utils.ApplicationDB;
import com.mybuy.model.UpdateUser;
import java.sql.*;

public class UpdateUserDAO implements IUpdateUserDAO {
	
	@Override
	public boolean updateUser(UpdateUser user) {
		System.out.println("got3");
		
		String sql;
		String id;
		if (user.getType().equals("endUser")) {
			id = "User_id";
		} else {
			id = "CustomerRep_id";
		}
		if (user.getField().equals("password")) {
			sql = "UPDATE " + user.getType() + " SET password = ?, salt = ? WHERE " + id + " = ?";
		} else {
			sql = "UPDATE " + user.getType() + " SET " + user.getField() + " = ? WHERE " + id + " = ?"; //James: Could lead to SQL injection hack but probably don't have to worry about that here
		}
		
		try (Connection conn = ApplicationDB.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, user.getValue());
			if (user.getField().equals("password")) {
				pstmt.setString(2, user.getSalt());
				pstmt.setString(3, user.getId());
			} else {
				pstmt.setString(2, user.getId());
			}
			
			int rowsAffected = pstmt.executeUpdate();
			
			System.out.println(rowsAffected);
			return true;
			
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}
}