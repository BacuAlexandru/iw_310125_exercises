package bacu.alex.exerciseOne;

import java.util.concurrent.Semaphore;

public class ExerciseOne {

    // Time in milliseconds for threads to run
    private static final int THREAD_RUN_TIME_MS = 5000;

    // semaphore objects used to ensure "ping" and "pong" are alternating
    // we set the ping one to 1 so that it's available first
    private static final Semaphore pingSemaphore = new Semaphore(1);
    private static final Semaphore pongSemaphore = new Semaphore(0);

    public static void execute() {
        // ping Thread
        Runnable pingTask = new Runnable() {
            @Override
            public void run() {
                long endTime = System.currentTimeMillis() + THREAD_RUN_TIME_MS;
                while (System.currentTimeMillis() < endTime) {
                    try {
                        // lock the ping Semaphore for the duration of this execution
                        pingSemaphore.acquire();
                        // print 'ping' message
                        System.out.println("ping");
                        // release pong Semaphore so that pong Thread can run
                        pongSemaphore.release();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };

        Runnable pongTask = new Runnable() {
            @Override
            public void run() {
                long endTime = System.currentTimeMillis() + THREAD_RUN_TIME_MS;
                while (System.currentTimeMillis() < endTime) {
                    try {
                        // lock the pong Semaphore for the duration of this execution
                        pongSemaphore.acquire();
                        // print 'pong' message
                        System.out.println("pong");
                        // release ping Semaphore so that ping Thread can run
                        pingSemaphore.release();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };

        // create ping and pong Threads
        Thread pingThread = new Thread(pingTask);
        Thread pongThread = new Thread(pongTask);

        // start both Threads
        pingThread.start();
        pongThread.start();

        try {
            // ensure the main Thread waits for ping & pong Threads to finish
            pingThread.join();
            pongThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
