package bacu.alex.exerciseOne;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ExerciseOne {
    // Time in milliseconds for threads to run
    private static final int THREAD_RUN_TIME_MS = 5000;

    // semaphore objects used to ensure "ping" and "pong" are alternating
    // we set the ping one to 1 permit so that it's available first
    private final Semaphore pingSemaphore = new Semaphore(1);
    private final Semaphore pongSemaphore = new Semaphore(0);

    // Thread pool for managing the two threads
    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    // Flag for graceful shutdown
    private volatile boolean isRunning = true;

    /**
     * Common method for both ping and pong
     *
     * @param message          The message to print 'ping' or 'pong'
     * @param acquireSemaphore The semaphore to acquire before printing
     * @param releaseSemaphore The semaphore to release after printing
     */
    private void runPingPong(String message, Semaphore acquireSemaphore, Semaphore releaseSemaphore) {
        long endTime = System.currentTimeMillis() + THREAD_RUN_TIME_MS;
        while (System.currentTimeMillis() < endTime && isRunning) {
            try {
                acquireSemaphore.acquire();
                System.out.println(message);
                releaseSemaphore.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    /**
     * Executes the ping-pong pattern for the specified duration
     * Creates and manages two threads that alternate between ping and pong
     */
    public void execute() {
        // Create tasks using lambda expressions
        final Runnable pingTask = () -> runPingPong("ping", pingSemaphore, pongSemaphore);
        final Runnable pongTask = () -> runPingPong("pong", pongSemaphore, pingSemaphore);

        // Submit tasks to executor
        executor.submit(pingTask);
        executor.submit(pongTask);

        try {
            // Wait for runnables to complete
            executor.awaitTermination(THREAD_RUN_TIME_MS + 500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.out.println("Execution interrupted");
            Thread.currentThread().interrupt();
        } finally {
            shutdown();
        }
    }

    /**
     * Performs cleanup by shutting down the executor and releasing semaphores
     */
    public void shutdown() {
        isRunning = false;
        executor.shutdownNow();
        pingSemaphore.release(1);
        pongSemaphore.release(1);
    }
}