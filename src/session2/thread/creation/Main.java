package session2.thread.creation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import session2.thread.creation.exercise.MultiExecutor;

public class Main {
    public static final int MAX_PASSWORD = 999;
    public static void main(String[] args) {
        Random random = new Random();
        int randomPassword = random.nextInt(MAX_PASSWORD);
        System.out.println("Random password is " + randomPassword);
        Vault vault = new Vault(randomPassword);

        List<Thread> threads = new ArrayList<>();
        threads.add(new AscendingHackerThread(vault));
        threads.add(new DescendingHackerThread(vault));
        threads.add(new PoliceThread());

        Runnable runnable = () -> {
            System.out.println("Runable " + Thread.currentThread().getName() + " is on the run");
        };

        runnable.run();
        MultiExecutor executor = new MultiExecutor(threads);
        executor.executeAll();
    }

    private static class Vault {
        private int password;

        public Vault(int password) {
            this.password = password;
        }

        public boolean isCorrectPassword(int guess) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return password == guess;
        }
    }

    private static abstract class HackerThread extends Thread {
        protected Vault vault;

        public HackerThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public void start() {
            System.out.println("Starting thread " + this.getName());
            super.start();
        }
    }

    private static class AscendingHackerThread extends HackerThread {

        public AscendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = 0; guess <= MAX_PASSWORD ; guess++) {
//                System.out.println(this.getName() + " is guessing at the password " + guess);
                if (this.vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class DescendingHackerThread extends HackerThread {

        public DescendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = MAX_PASSWORD; guess >= 0 ; guess--) {
//                System.out.println(this.getName() + " is guessing at the password " + guess);
                if (this.vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class PoliceThread extends Thread {
        @Override
        public void run() {
            for (int i = 10; i >= 0 ; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(i);
            }
            System.out.println("Time up, catch hackers");
            System.exit(0);
        }
    }
}