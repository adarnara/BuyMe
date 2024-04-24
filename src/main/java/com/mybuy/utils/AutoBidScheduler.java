package com.mybuy.utils;

import java.util.concurrent.*;

public class AutoBidScheduler {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1); // Adarsh: keeping this as one thread to executre sequentially to ignore race conditions. if need be will add threading enviornment if we have to deal with multiple requests.

    public void scheduleAutoBid(Runnable task, long delay, TimeUnit timeUnit) {
        scheduler.scheduleAtFixedRate(task, 0, delay, timeUnit);
    }

    public void shutdownScheduler() {
        scheduler.shutdown();
    }
}
