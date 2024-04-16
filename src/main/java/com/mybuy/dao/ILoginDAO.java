package com.mybuy.dao;

import com.mybuy.model.Login;

public interface ILoginDAO {
    Login getUserByUsernameOrEmail(String usernameOrEmail);
}