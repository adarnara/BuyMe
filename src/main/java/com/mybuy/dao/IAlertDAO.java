package com.mybuy.dao;

import com.mybuy.model.Alert;

import java.util.List;

public interface IAlertDAO {
    List<Alert> checkAndNotify(int userId);
}
