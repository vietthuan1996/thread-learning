package session6.thread.atomicandsynchronization.examplevolatile;

import java.util.Random;

public class MinMaxMetricMonitor {

    public static void main(String[] args) {
        MinMaxMetric metrics = new MinMaxMetric(Long.MIN_VALUE, Long.MAX_VALUE);
        BusinessLogic first = new BusinessLogic(metrics);
        BusinessLogic second = new BusinessLogic(metrics);
        PrinterBusinessLogic printerBusinessLogicForMin = new PrinterBusinessLogic(metrics, true);
        PrinterBusinessLogic printerBusinessLogicForMax = new PrinterBusinessLogic(metrics, false);

        first.start();
        second.start();
        printerBusinessLogicForMin.start();
        printerBusinessLogicForMax.start();
    }

    public static class PrinterBusinessLogic extends Thread {
        private final MinMaxMetric metrics;
        private final boolean isMin;


        public PrinterBusinessLogic(MinMaxMetric metrics, boolean isMin) {
            this.metrics = metrics;
            this.isMin = isMin;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
                long val = isMin ? metrics.getMin() : metrics.getMax();
                if (isMin) {
                    System.out.println("MIN VALUE IS " + val + " ON " + Thread.currentThread().getName());
                } else {
                    System.out.println("MAX VALUE IS " + val + " ON " + Thread.currentThread().getName());
                }
            }
        }
    }

    public static class BusinessLogic extends Thread {
        private final MinMaxMetric metrics;
        private final Random random = new Random();

        public BusinessLogic(MinMaxMetric metrics) {
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
    public static class MinMaxMetric {
        private volatile long min;
        private volatile long max;

        public MinMaxMetric(long min, long max) {
            this.min = min;
            this.max = max;
        }

        public synchronized void addSample(long sample) {
           this.min = Math.min(sample, min);
           this.max = Math.max(sample, max);
        }

        public long getMin() {
            return min;
        }

        public long getMax() {
            return max;
        }
    }
}
