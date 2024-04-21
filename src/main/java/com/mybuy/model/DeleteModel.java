package com.mybuy.model;

import com.mybuy.dao.IDeleteDAO;
import com.mybuy.dao.DeleteDAO;

public class DeleteModel {
	
	private IDeleteDAO deleteDAO;
	
	public DeleteModel() {
		this.deleteDAO = new DeleteDAO();
	}
	
	public boolean deleteUser(Delete user) {
		System.out.println("Here3");
		return deleteDAO.delete(user);
	}
}