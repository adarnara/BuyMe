package com.mybuy.model;

import com.mybuy.dao.IDeleteDAO;
import com.mybuy.dao.DeleteDAO;

public class DeleteModel {
	
	private IDeleteDAO deleteDAO;
	
	public DeleteModel() {
		this.deleteDAO = new DeleteDAO();
	}
	
	public boolean deleteUser(Delete user) {
		return deleteDAO.delete(user);
	}
	
	public boolean deleteAuction(Delete auction) {
		return deleteDAO.deleteAuction(auction);
	}
	
	public boolean deleteBid(Delete bid) {
		return deleteDAO.deleteBid(bid);
	}
}