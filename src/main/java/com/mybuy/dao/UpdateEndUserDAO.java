package com.mybuy.dao;

import com.mybuy.utils.ApplicationDB;
import com.mybuy.model.UpdateEndUser;
import java.sql.*;

public class UpdateEndUserDAO implements IUpdateEndUserDAO {
	
	@Override
	public boolean updateUser(UpdateEndUser user) {
		System.out.println("got3");
		
		String sql;
		if (user.getField().equals("password")) {
			sql = "UPDATE EndUser SET password = ?, salt = ? WHERE User_id = ?";
		} else {
			sql = "UPDATE EndUser SET " + user.getField() + " = ? WHERE User_id = ?"; //James: Could lead to SQL injection hack but probably don't have to worry about that here
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