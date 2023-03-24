package session2.thread.creation.exercise;

import java.util.List;

public class MultiExecutor {
    private final List<Thread> threads;

    /*
     * @param tasks to executed concurrently
     */
    public MultiExecutor(List<Thread> tasks) {
        threads = tasks;
    }

    /**
     * Starts and executes all the tasks concurrently
     */
    public void executeAll() {
        for(Thread thread : threads) {
            thread.start();
        }
    }
}
