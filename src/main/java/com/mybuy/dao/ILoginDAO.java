package com.mybuy.dao;

import com.mybuy.model.Auction;
import com.mybuy.model.Login;

import java.util.List;

public interface ILoginDAO {
    Login getUserByUsernameOrEmail(String usernameOrEmail);
    String getEndUserType(String username);
    int getUserId(String username);
    String getUsername(int userId);
}