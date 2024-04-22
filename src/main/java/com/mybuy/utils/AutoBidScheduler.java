package com.mybuy.utils;

import java.util.concurrent.*;

public class AutoBidScheduler {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void scheduleAutoBid(Runnable task, long delay, TimeUnit timeUnit) {
        scheduler.scheduleAtFixedRate(task, 0, delay, timeUnit);
    }

    public void shutdownScheduler() {
        scheduler.shutdown();
    }
}
