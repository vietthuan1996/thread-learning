package session3.thread.coordination.thread.join;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Learns about Thread join() method
 * The usage of this method is that to
 * - Waits until concurrent threads finished and take the result.
 * - Uses join() method with limit param to config a threshold of execution time.
 * - Set a thread as daemon for peaceful termination.
 */
// 3632
public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(0L, 73435L, 7354L, 72334L, 75555L, 722L, 3434L, 5566L);

        List<FactorialThread> threads = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        for (Long num: inputNumbers) {
            BigInteger tempResult = BigInteger.ONE;

            for (long i = num; i > 0; i--) {
                tempResult = tempResult.multiply(BigInteger.valueOf(num));
            }
            System.out.println(tempResult);
        }
//        for (Long num: inputNumbers) {
//            threads.add(new FactorialThread(num));
//        }
//
//        for (Thread thread: threads) {
//            thread.setDaemon(true);
//            thread.start();
//        }
//
//        for (Thread thread: threads) {
//            thread.join();
//        }
//
//        for (FactorialThread factorialThread : threads) {
//            if (factorialThread.isFinished()) {
//                System.out.println(factorialThread.getName() + ", factorial of " + factorialThread.inputNumber +  " result: " + factorialThread.getResult());
//            } else {
//                System.out.println(factorialThread.getName() + ", factorial of " + factorialThread.inputNumber +  " is in process");
//            }
//        }
        System.out.println("--------------------------------");
        System.out.println(System.currentTimeMillis()-startTime);
    }

    public static class FactorialThread extends Thread {
        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            System.out.println("Run on " + Thread.currentThread().getName());
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        public BigInteger factorial(long n) {
            BigInteger tempResult = BigInteger.ONE;

            for (long i = n; i > 0; i--) {
                tempResult = tempResult.multiply(BigInteger.valueOf(n));
            }
            return tempResult;
        }

        public BigInteger getResult() {
            return result;
        }

        public boolean isFinished() {
            return isFinished;
        }
    }
}
