package com.diliprathore.java;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(0L, 3435L, 35435L, 2324L, 4656L, 23L, 5556L);
        List<FactorialThread> threads = new ArrayList<>();

        for (Long item: inputNumbers) {
            threads.add(new FactorialThread(item));
        }

        for (FactorialThread thread: threads) {
            thread.setDaemon(true);
            thread.start();
        }

        for (FactorialThread thread: threads) {
            thread.join(2000); // This will make sure Main thread will wait until all the factorial threads are finished.
            // Main thread will exit from this loop ONLY when all the threads are finished OR if 2000 ms time has been passed for all the threads
        }

        for (int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread thread = threads.get(i);
            if (thread.isFinished()) {
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + thread.getResult());
            } else {
                System.out.println("The calculation of " + inputNumbers.get(i) + " is still in progress");
            }
        }
    }

    public static class FactorialThread extends Thread {
        public BigInteger getResult() {
            return result;
        }

        public boolean isFinished() {
            return isFinished;
        }

        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            this.result = factorial(this.inputNumber);
            this.isFinished = true;
        }

        private BigInteger factorial(long n) {
            BigInteger tempResult = BigInteger.ONE;
            for (long i = n; i > 0; i--) {
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }
            return tempResult;
        }
    }
}
