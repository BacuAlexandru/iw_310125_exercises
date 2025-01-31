package bacu.alex.exerciseOne;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Create a Thread that prints a specific message
 */
public class PingPongThreads {
    /**
     * Creates a Thread that prints a specific message
     *
     * @param lock Synchronization object used for locking the threads
     * @param running AtomicBoolean variable used for controlling the status of the Thread
     *                If it's supposed to be still running (true) or stop (false)
     * @param message What text it will print
     * @return A Thread object
     */
    public static Thread createPingPongThread(Object lock, AtomicBoolean running, String message) {
        return new Thread(() -> {
            // execute as long as running is true
            while (running.get()) {
                synchronized (lock) {
                    // print desired message
                    System.out.println(message);
                    // sleep 100ms, just so the messages are more human-readable
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
                    // notify other synced thread
                    lock.notify();
                    // wait for lock to be released by other thread
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
    }
}
