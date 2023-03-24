package session6.thread.atomicandsynchronization.examplevolatile;

public class DataRace {

    public static void main(String [] args) {
        SharedClass sharedClass = new SharedClass();
        Thread threadA = new Thread(() -> {
            for (int i = Integer.MAX_VALUE; i > Integer.MIN_VALUE; i--) {
                sharedClass.increment();
            }
        });

        Thread threadB = new Thread(() -> {
            for (int i = Integer.MAX_VALUE; i > Integer.MIN_VALUE; i--) {
                sharedClass.checkDataRace();
            }
        });

        threadA.start();
        threadB.start();
    }
    public static class SharedClass {
        private volatile int x = 0;
        private volatile int y = 0;

        public void increment() {
            x++;
            y++;
        }

        public void checkDataRace() {
            if (y > x) {
                System.out.println("Data race happened");
            }
        }
    }
}
