package bacu.alex.exerciseOne;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseOneTest {
    // default run time in milliseconds for the threads to run
    private static final int THREAD_RUN_TIME_MS = 5000;
    // get System Line Separator so there aren't any issues
    private static final String LINE_SEPARATOR = System.lineSeparator();
    // helper variables to capture System out print stream

    private ByteArrayOutputStream baos;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        baos = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(baos));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    private String pingPong() {
        ExerciseOne one = new ExerciseOne();
        one.execute();
        return baos.toString();
    }

    @RepeatedTest(5)
    void testPingPongAlternation() {
        String result = pingPong();
        String[] lines = result.split(LINE_SEPARATOR);

        assertTrue(lines.length > 0, "Verify that there is some output");

        for (int i = 0; i < lines.length; i++) {
            String expected = i % 2 == 0 ? "ping" : "pong";
            assertEquals(expected, lines[i].trim(), "Line " + i + " should be " + expected);
        }
    }

    @RepeatedTest(5)
    void testExecutionTime() {
        long start = System.currentTimeMillis();
        pingPong();
        long duration = System.currentTimeMillis() - start;

        assertTrue(duration >= THREAD_RUN_TIME_MS, "Execution should take at least THREAD_RUN_TIME_MS");
        assertTrue(duration < THREAD_RUN_TIME_MS + 1000, "Execution should not take too much longer than THREAD_RUN_TIME_MS");
    }

    @RepeatedTest(5)
    void testInterruptedExceptionHandling() {
        Thread testThread = new Thread(this::pingPong);

        testThread.start();
        testThread.interrupt();

        try {
            testThread.join(THREAD_RUN_TIME_MS + 1000);
            assertFalse(testThread.isAlive(), "Thread should terminate after interruption");
        } catch (InterruptedException e) {
            fail("Test thread join was interrupted");
        }
    }
}