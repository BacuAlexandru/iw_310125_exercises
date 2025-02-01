package bacu.alex;

import bacu.alex.exerciseOne.ExerciseOne;
import bacu.alex.exerciseTwo.Solution;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        // if not arguments are passed by default run both exercise
        if (args.length == 0) {
            System.out.println("Default behaviour: running both exercises");
            runBothExercises();
            return;
        }

        // parse arguments into a map (expecting the format key=value)
        Map<String, String> params = new HashMap<>();
        for (String arg : args) {
            String[] parts = arg.split("=", 2);
            if (parts.length == 2) {
                params.put(parts[0].toLowerCase(), parts[1]);
            }
        }

        // if arguments were provided one of them has to be "exercise"
        if (!params.containsKey("exercise")) {
            System.out.println("Missing required parameter: exercise");
            return;
        }

        String exerciseNumber = params.get("exercise");
        if ("1".equals(exerciseNumber)) {
            runExerciseOne();
        } else if ("2".equals(exerciseNumber)) {
            System.out.println("Exercise 2 selected.");

            // if optional "series" parameter is provided then use the series provided by the user
            if (params.containsKey("series")) {
                String seriesStr = params.get("series");
                // split the series by comma and transform it to a int array
                String[] seriesParts = seriesStr.split(",");
                int[] seriesNumbers = new int[seriesParts.length];
                try {
                    for (int i = 0; i < seriesParts.length; i++) {
                        seriesNumbers[i] = Integer.parseInt(seriesParts[i]);
                    }
                    runExerciseTwo(seriesNumbers);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid format for series " + seriesStr + ". Please provide integers separated by commas.");
                }
            } else {
                // if not "series" parameter was provided run Exercise 2 with the default series
                System.out.println("Running Exercise 2 with default series");

                System.out.println("EXERCISE TWO");
                int[] A = new int[]{130, 191, 200, 10};
                runExerciseTwo(A);

                A = new int[]{405, 45, 300, 300};
                runExerciseTwo(A);

                A = new int[]{50, 222, 49, 52, 25};
                runExerciseTwo(A);

                A = new int[]{30, 909, 3190, 99, 3990, 9009};
                runExerciseTwo(A);
            }
        } else {
            System.out.println("Invalid value for exercise. Use exercise=1 or exercise=2.");
        }

    }

    private static void runBothExercises() {
        runExerciseOne();

        System.out.println("EXERCISE TWO");
        int[] A = new int[]{130, 191, 200, 10};
        runExerciseTwo(A);

        A = new int[]{405, 45, 300, 300};
        runExerciseTwo(A);

        A = new int[]{50, 222, 49, 52, 25};
        runExerciseTwo(A);

        A = new int[]{30, 909, 3190, 99, 3990, 9009};
        runExerciseTwo(A);
    }

    private static void runExerciseOne() {
        System.out.println("EXERCISE ONE");
        System.out.println("~~~BEGIN PING PONG~~~");
        ExerciseOne one = new ExerciseOne();
        one.execute();
        System.out.println("~~~END PING PONG~~~");
    }

    private static void runExerciseTwo(int[] A) {
        int maxSum = Solution.solution(A);
        System.out.println("Max sum for " + Arrays.toString(A) + " is: " + maxSum);
    }
}