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
        try (Connection conn = ApplicationDB.getConnection();
            	PreparedStatement pstmt = conn.prepareStatement(sql)) {
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                System.out.println("Couldn't delete user");
                return false;
            }
	}
	
	public boolean deleteAuction(Delete auction) {
		String sql = "DELETE FROM Auction WHERE Auction_id = " + auction.getId(); 
        try (Connection conn = ApplicationDB.getConnection();
            	PreparedStatement pstmt = conn.prepareStatement(sql)) {
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
        } catch (SQLException e) {
        	System.out.println("Couldn't delete auction");
            return false;
        }
	}
	
	public boolean deleteBid(Delete bid) {
		String sql = "DELETE FROM Bid WHERE Bid_id = " + bid.getId(); 
        try (Connection conn = ApplicationDB.getConnection();
            	PreparedStatement pstmt = conn.prepareStatement(sql)) {
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
        } catch (SQLException e) {
        	System.out.println("Couldn't delete bid");
            return false;
        }
	}
}
