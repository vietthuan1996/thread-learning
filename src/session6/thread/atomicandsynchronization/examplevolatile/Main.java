package session6.thread.atomicandsynchronization.examplevolatile;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Metrics metrics = new Metrics();
        BusinessLogic first = new BusinessLogic(metrics);
        BusinessLogic second = new BusinessLogic(metrics);
        PrinterBusinessLogic printerBusinessLogic = new PrinterBusinessLogic(metrics);

        first.start();
        second.start();
        printerBusinessLogic.start();
    }

    public static class PrinterBusinessLogic extends Thread {
        private Metrics metrics;

        public PrinterBusinessLogic(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
                double average = metrics.getAverage();
                System.out.println("Current average is " + average);
            }
        }
    }

    public static class BusinessLogic extends Thread {
        private final Metrics metrics;
        private final Random random = new Random();

        public BusinessLogic(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while(true) {
                long start = System.currentTimeMillis();
                try {
                    Thread.sleep(random.nextInt(100));
                } catch (InterruptedException ignored) {
                }
                long end = System.currentTimeMillis();
                metrics.addSample(end - start);
            }
        }
    }

    public static class Metrics {
        private long count = 0;
        /**
         * The usage of volatile is that to make sure the visibility of shared resource between thread.
         * @see <very useful link https://www.baeldung.com/java-volatile>
         */
        private volatile double average = 0.0;

        public synchronized void addSample(long sample) {
            double currentSum = average * count;
            count++;
            average = (currentSum + sample) / count;
        }

        public double getAverage() {
            return average;
        }
    }
}
