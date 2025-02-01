package bacu.alex;

import bacu.alex.exerciseOne.ExerciseOne;
import bacu.alex.exerciseTwo.Solution;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

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
        ExerciseOne.execute();
        System.out.println("~~~END PING PONG~~~");
    }

    private static void runExerciseTwo(int[] A) {
        int maxSum = Solution.solution(A);
        System.out.println("Max sum for " + Arrays.toString(A) +  " is: " + maxSum);
    }
}