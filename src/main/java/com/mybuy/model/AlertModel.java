package com.mybuy.model;

import com.mybuy.dao.AlertDAO;
import com.mybuy.dao.IAlertDAO;
import com.mybuy.utils.AlertScheduler;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AlertModel {
    private IAlertDAO alertDAO = new AlertDAO();
    private AlertScheduler scheduler;

    public AlertModel() {
        this.scheduler = new AlertScheduler();
    }

    public void startAlertScheduler(int userId) {
        scheduler.scheduleTask(() -> runAlertCheck(userId), 0, 5, TimeUnit.MINUTES);
    }

    public List<Alert> runAlertCheck(int userId) {
        return alertDAO.checkAndNotify(userId);
    }

    public void shutdownScheduler() {
        scheduler.shutdown();
    }
}
