package bacu.alex.exerciseOne;

import org.junit.jupiter.api.RepeatedTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.Assert.*;

class ExerciseOneTest {

    private String pingPong() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);

        ExerciseOne.execute();

        String result = baos.toString();

        System.setOut(System.out);

        return result;
    }

    @RepeatedTest(5)
    void testPingPongStartsWithPing() {
        String result = pingPong();

        assertTrue(result.startsWith("ping"));

    }

    @RepeatedTest(5)
    void testPingPongAlternation() {
        String result = pingPong();
        String lineSeparator = System.lineSeparator();

        assertTrue("Always have alternations between 'ping' and 'pong'",
                result.contains("ping" + lineSeparator + "pong") || result.contains("pong" + lineSeparator + "ping"));

    }

    @RepeatedTest(5)
    void testPingPongNoDuplicate() {
        String result = pingPong();
        String lineSeparator = System.lineSeparator();

        assertFalse("Never have 'ping' or 'pong' one after another",
                result.contains("ping" + lineSeparator + "ping") || result.contains("pong" + lineSeparator + "pong"));

    }

}