package com.mybuy.model;

import com.mybuy.dao.IUpdateUserDAO;
import com.mybuy.dao.UpdateUserDAO;
import com.mybuy.utils.HashingUtility;

public class UpdateUserModel {
	
	private IUpdateUserDAO updateUserDAO;
	
	public UpdateUserModel() {
		this.updateUserDAO = new UpdateUserDAO();
	}
	
	public boolean updateUser(UpdateUser update) {
		try {
			if (update.getField().equals("password")) {
				String salt = HashingUtility.generateSalt();
				String hashedPassword = HashingUtility.hashPassword(update.getValue(), salt);
				update.setValue(hashedPassword);
				update.setSalt(salt);
			}
			System.out.println("got2");
			return updateUserDAO.updateUser(update);
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
}