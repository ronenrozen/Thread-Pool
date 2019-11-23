import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
    private final int numberOfThreads;
    private final LinkedBlockingQueue tasksList;
    private final Worker[] threads;

    public ThreadPool(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        tasksList = new LinkedBlockingQueue();
        threads = new Worker[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Worker(tasksList);
            threads[i].start();
        }
    }

    public void run(Callable task) {
        synchronized (tasksList) {
            tasksList.add(task);
            tasksList.notify();
        }
    }
}