package com.mybuy.model;

import com.mybuy.dao.IRegisterDAO;
import com.mybuy.dao.RegisterDAO;
import com.mybuy.utils.HashingUtility;

public class RegisterModel {

    private IRegisterDAO registerDAO;

    public RegisterModel() {
        this.registerDAO = new RegisterDAO();
    }

    public boolean insertUser(Register register) {
        try {
            String salt = HashingUtility.generateSalt();
            String hashedPassword = HashingUtility.hashPassword(register.getPassword(), salt);
            register.setPassword(hashedPassword);
            register.setSalt(salt);
            return registerDAO.insertUser(register);
        }
        catch (Exception e) {
            System.out.println("Couldn't set password");
            return false;
        }
    }
}