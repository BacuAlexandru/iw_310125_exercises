package bacu.alex.exerciseTwo;

import java.util.*;

public class Solution {
    public static int solution(int[] A) {
        // if we don't have at least 2 integers in the array return -1 directly
        // since without at least 2 integers we cannot find a pair of numbers
        if (A.length < 2) {
            return -1;
        }

        // contains key-value pairs where:
        // key: is a "<first-digit>-<last-digit>" string extracted from a number
        // value: is a int array with exactly 2 elements, that contains the 2 largest numbers that share the same first/last digit as the key
        Map<String,int[]> shareDigitsGroup = new HashMap<>();

        for (int number : A) {

            // extract first and last digit string from a number
            // this will be our key in the Map
            String firstLastDigitKey = getFirstAndLastDigit(number);

            // if there is no element in the map for the extracted key
            // add a new element where both values are Integer.MIN_VALUE
            // thus any new number we find will be greater than them
            shareDigitsGroup.putIfAbsent(firstLastDigitKey, new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE});

            // check if the number we calculated first and last digit for is bigger than any of the stored values particular key combination
            int[] biggestNumbers = shareDigitsGroup.get(firstLastDigitKey);

            if (number > biggestNumbers[0]) {
                biggestNumbers[1] = biggestNumbers[0];
                biggestNumbers[0] = number;
            } else if (number > biggestNumbers[1]) {
                biggestNumbers[1] = number;
            }
        }

        // calculate maximum sum
        int maxSum = -1;
        // iterate through every first-last digit key combination we found in the array
        for(int[] group: shareDigitsGroup.values()) {
            // for each one only take it into account if the second number is different that MIN_VALUE
            // this ensures that we only do it for first-last digit combinations that we found 2 numbers
            if(group[1] != Integer.MIN_VALUE) {
                // update maximum sum with the sum of the two numbers only if it's bigger than the current maximum sum
                maxSum = Math.max(maxSum, group[0] + group[1]);
            }
        }

        return maxSum;
    }

    /**
     * First and last digit of an integer number.
     * For example, if number is 98280 return '9-0'
     *
     * @param number Number that we want to find out digits for
     * @return A string in the format of FirstDigit-LastDigit
     */
    public static String getFirstAndLastDigit(int number) {
        // special rule for 0, since logarithm for 0 is undefined
        if (number == 0) {
            return "0-0";
        }

        // get the absolute value of the number
        // this way we are taking into account possible negative integers
        number = Math.abs(number);

        // for the first digit, establish the number of digits minus 1
        int numberOfDigits = (int)(Math.log10(number));
        // then divide the number by 10 to the power of the number of digits - 1
        int firstDigit = number / (int)(Math.pow(10, numberOfDigits));
        // calculate last digit
        int lastDigit = number % 10;

        return firstDigit + "-" + lastDigit;

    }
}
