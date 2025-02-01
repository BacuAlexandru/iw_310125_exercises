# Java Threading Problems

## Introduction

This project is work for Alexandru Bacu's interview for DB Global Technology.

There were 2 problems that needed to be solved:

1. **Exercise 1**: Two threads ping-pong. One thread prints 'ping', another one prints' pong'. Stop after 5 seconds

Expected output is alternating ping-pong :

ping

pong

ping

pong

ping

pong
2. **Exercise 2**: There is an array A consisting of N integers. What is the maximum sum of two integers from A that share their first and last digits? For example, 1007 and 167 share their first (1) and last (7) digits, whereas 2002 and 55 do not.

Write a function:


```class Solution { public int solution(int[] A); }```

that, given an array A consisting of N integers, returns the maximum sum of two integers that share their first and last digits. If there are no two integers that share their first and last digits, the function should return −1.

Examples:

1. Given A = [130, 191, 200, 10], the function should return 140. The only integers in A that share first and last digits are 130 and 10.

2. Given A = [405, 45, 300, 300], the function should return 600. There are two pairs of integers that share first and last digits: (405, 45) and (300, 300). The sum of the two 300s is bigger than the sum of 405 and 45.

3. Given A = [50, 222, 49, 52, 25], the function should return −1. There are no two integers that share their first and last digits.

4. Given A = [30, 909, 3190, 99, 3990, 9009], the function should return 9918.

## Implementation
For implementing the solutions I've chosen Java, as it is language I have the most recent extensive experience in.

### Exercise 1 Solution
- Uses Semaphores for thread synchronization, so that we alternate between 'ping' & 'pong' messages
- Implements ExecutorService for thread management
- Write unit tests that ensure:
  - messages are alternating between 'ping' and 'pong'
  - messages are always starting with 'ping'
  - the threads are running for at least 5 seconds
  - interruptions are handles

### Exercise 2 Solution
- Use a key-value map, where key is a combination <first-digit>-<last-digit> and for each key only keep the highest 2 numbers from the array that share the same first and last digits
- Use a math equation to determine the first digit to help with performance
  - (int) log base 10 from a number will give us the number of digits minus 1
  - the number divide by 10 to the power of (the number of digits - 1) will give us the first digit
- Handle edge case: single element array, 0 digit, negative integers
- Write unit tests to cover various cases, by reading the array and expected values from an csv

## Build Instructions

```bash
# Clone repository
git clone https://github.com/BacuAlexandru/iw_310125_exercises.git

# Package with Maven
mvn clean package
```
The package will create a uber-jar with all dependencies ```exercises/target/exercises-1.0-SNAPSHOT-jar-with-dependencies.jar```

## Testing

Run unit tests using Maven:
```bash
mvn test
```

## Alternative to building
You can download the JAR from release v1.0.0 https://github.com/BacuAlexandru/iw_310125_exercises/releases/tag/v1.0.0

## Using application to view solution
Provided you have either directly downloaded the uber-jar from the release or built it locally, once you have you can run the following commands
```bash
java -jar .\exercises-1.0-SNAPSHOT-jar-with-dependencies.jar
```
This will run both exercises with the values provided in the original email request

```bash
java -jar .\exercises-1.0-SNAPSHOT-jar-with-dependencies.jar exercise=1
```

This will run only exercise 1, and show the ping-pong messages

```bash
java -jar .\exercises-1.0-SNAPSHOT-jar-with-dependencies.jar exercise=2
```

This will run exercise 2 with the arrays provided in the original email request

```bash
java -jar .\exercises-1.0-SNAPSHOT-jar-with-dependencies.jar exercise=2 series=3009,39,881,80071,99,9
```

This allows the user to run exercise 2 with a custom series of numbers to verify

## GitHub Actions

1. **Java CI with Maven** will build, test and display test results
2. **On-Demand Release** will create a new tag and release based on the version inputted by the user
3. **Sample Exercises** allows to test solution directly through GitHub actions

For **Sample Exercises** the user can select to:
- *all*: will run both exercises with default values and create an output artifact with the log
- *1*: will run exercise 1 and create an output artifact with the log
- *2*: will run exercise 2 with the default values in the request
  - optionally here you can add a custom array to test exercise 2 in the *series* field

