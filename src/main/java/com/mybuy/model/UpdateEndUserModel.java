package com.mybuy.model;

import com.mybuy.dao.IUpdateEndUserDAO;
import com.mybuy.dao.UpdateEndUserDAO;
import com.mybuy.utils.HashingUtility;

public class UpdateEndUserModel {
	
	private IUpdateEndUserDAO updateEndUserDAO;
	
	public UpdateEndUserModel() {
		this.updateEndUserDAO = new UpdateEndUserDAO();
	}
	
	public boolean updateUser(UpdateEndUser update) {
		try {
			if (update.getField().equals("password")) {
				String salt = HashingUtility.generateSalt();
				String hashedPassword = HashingUtility.hashPassword(update.getValue(), salt);
				update.setValue(hashedPassword);
				update.setSalt(salt);
			}
			System.out.println("got2");
			return updateEndUserDAO.updateUser(update);
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
}