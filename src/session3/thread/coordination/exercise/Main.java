package session3.thread.coordination.exercise;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        PowerCalculatingThread firstCal = new PowerCalculatingThread(BigInteger.valueOf(5L), BigInteger.valueOf(100000000L));
        PowerCalculatingThread secondCal = new PowerCalculatingThread(BigInteger.valueOf(6L), BigInteger.valueOf(1L));

        firstCal.setDaemon(true);
        secondCal.setDaemon(true);
        firstCal.start();
        secondCal.start();

        firstCal.join(2000);
        secondCal.join();

        BigInteger result = BigInteger.ZERO;
        result = result.add(firstCal.getResult()).add(secondCal.getResult());
        System.out.println(result);
    }

    private static class PowerCalculatingThread extends Thread {
        private final BigInteger base;
        private final BigInteger power;

        private BigInteger result = BigInteger.ZERO;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println("Start the calculation for " + Thread.currentThread().getName());
            result = calculate();

        }

        public BigInteger calculate() {
            BigInteger result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) < 0; i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }
            return result;
        }

        public BigInteger getResult() {
            return result;
        }
    }
}
