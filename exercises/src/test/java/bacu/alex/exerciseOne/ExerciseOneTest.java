package bacu.alex.exerciseOne;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicBoolean;

import static junit.framework.Assert.*;

class ExerciseOneTest {

    @Test
    void testThreadCreation() {
        Object lock = new Object();
        AtomicBoolean running = new AtomicBoolean(true);

        Thread thread = PingPongThreads.createPingPongThread(lock, running, "test");
        assertNotNull(thread);
        assertFalse(thread.isAlive());
    }

    @RepeatedTest(5)
    void testPingPongExecution() throws InterruptedException {
        String lineSeparator = System.lineSeparator();
        Object lock = new Object();
        AtomicBoolean running = new AtomicBoolean(true);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Thread pingThread = PingPongThreads.createPingPongThread(lock, running, "ping");
        Thread pongThread = PingPongThreads.createPingPongThread(lock, running, "pong");

        pingThread.start();
        pongThread.start();

        Thread.sleep(5000);
        running.set(false);

        synchronized (lock) {
            lock.notifyAll();
        }

        pingThread.join();
        pongThread.join();
        System.setOut(System.out);

        String output = outputStream.toString();

        // verify that output contains alternations between "ping" and "pong"
        assertTrue("Always have alternations between 'ping' and 'pong'", output.contains("ping" + lineSeparator + "pong") || output.contains("pong" + lineSeparator + "ping"));

        assertFalse("Never have 'ping' or 'pong' one after another", output.contains("ping" + lineSeparator + "ping") || output.contains("pong" + lineSeparator + "pong")); // Ensuring alternation

        assertTrue("Output always starts with 'ping'", output.startsWith("ping"));

    }

}