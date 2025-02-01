package bacu.alex.exerciseTwo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @ParameterizedTest
    @CsvSource({
            "981,9-1",
            "20,2-0",
            "68910278,6-8", // bigger number
            "9,9-9", // single digit positive number
            "0,0-0", // special edge case 0
            "-701,7-1", // negative number
            "-6,6-6" // single digit negative number
    })
    void testFirstLastDigitExtraction(int number, String expected) {
        assertEquals(expected, Solution.getFirstAndLastDigit(number));
    }

    @ParameterizedTest
    @CsvFileSource(
            resources = "/testData.csv",
            delimiterString = ";",
            numLinesToSkip = 1
    )
    void testSolution(String series, int expected) {
        int[] inputArray = Arrays.stream(series.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        assertEquals(expected, Solution.solution(inputArray));
    }
}