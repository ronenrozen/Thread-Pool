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

    public double[] run(Callable task) {
        synchronized (tasksList) {
            tasksList.add(task);
            tasksList.notify();
        }
        for (Worker<double[]> w : threads) {
            try {
                synchronized (w) {
                    if (w.getCurrentTask() != null && w.getCurrentTask().equals(task)) {
                        w.notify();
                        return w.getResults();
                    }
                }
            } catch (Exception e) {
                //nothing
            }
        }
        return null;
    }
}