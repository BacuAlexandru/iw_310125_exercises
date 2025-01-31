package bacu.alex.exerciseOne;

import java.util.concurrent.atomic.AtomicBoolean;

public class ExerciseOne {
    public static void execute() {
        Object lock = new Object();
        AtomicBoolean running = new AtomicBoolean(true);

        // Create the "ping" and the "pong" threads
        Thread pingThread = PingPongThreads.createPingPongThread(lock, running, "ping");
        Thread pongThread = PingPongThreads.createPingPongThread(lock, running, "pong");

        // start both threads
        pingThread.start();
        pongThread.start();

        // wait 5 seconds for the threads to print the messages
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // after 5 seconds signal the threads to stop
        running.set(false);

        // notify all threads
        synchronized (lock) {
            lock.notifyAll();
        }

        // wait for threads to finish execution to avoid any potential issues
        try {
            pingThread.join();
            pongThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
