package com.mybuy.utils;

import java.util.concurrent.*;

public class AutoBidScheduler {

    // Adarsh: keeping this as one thread to executre sequentially to ignore race conditions. if need be will add threading enviornment if we have to deal with multiple requests.
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public Future<?> scheduleAutoBid(Runnable task, long initialDelay, TimeUnit unit) {
        return scheduler.schedule(task, initialDelay, unit);
    }
    public Future<?> scheduleAtFixedRate(Runnable task, long initialDelay, long period, TimeUnit unit) {
        return scheduler.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    public void shutdownScheduler() {
        scheduler.shutdown();
    }
}
