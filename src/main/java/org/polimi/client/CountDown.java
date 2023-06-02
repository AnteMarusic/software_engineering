package org.polimi.client;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CountDown {
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        // Create a Runnable task
        Runnable task = () -> {
            // Task logic to be executed
            System.out.println("Task executed");
        };

        // Schedule the task to be executed after a delay of 5 seconds
        long delay = 5; // Delay in seconds
        executor.schedule(task, delay, TimeUnit.SECONDS);

        // Shutdown the executor service after all tasks are executed
        executor.shutdown();
    }
}