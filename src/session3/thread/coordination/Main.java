package session3.thread.coordination;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new LongComputationTask(BigInteger.valueOf(Long.parseLong("20000000")), BigInteger.valueOf(
            Long.parseLong("2000000000"))));
        // Set a thread as Daemon thread in order to terminate it easily.
        thread.setDaemon(true);
        thread.start();

        Thread.sleep(1000);
        thread.interrupt();
    }

    /**
     * Interrupt a user thread using InterruptedException
     */
    private static class BlockingTask implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(500000000);
            } catch (InterruptedException e) {
                System.out.println("Exiting blocking thread");
            }
        }
    }

    private static class LongComputationTask implements Runnable {
        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base+"^"+power+" = " + pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                /**
                 * Interrupt a user thread using isInterrupted method
                 */
                //                if (Thread.currentThread().isInterrupted()) {
//                    System.out.println("Thread is interrupted");
//                    return BigInteger.ZERO;
//                }
                result = result.multiply(base);
            }

            return result;
        }
    }
}
