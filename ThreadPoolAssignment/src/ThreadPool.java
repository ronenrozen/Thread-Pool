import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class ThreadPool {
    private final BlockingQueue<iTask<?>> tasksList;
    private final BlockingQueue<?> results;
    private final Worker[] threads;

    ThreadPool(int numberOfThreads) {
        tasksList = new LinkedBlockingQueue<>();
        results = new LinkedBlockingQueue<>();
        threads = new Worker[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            //noinspection unchecked
            threads[i] = new Worker(tasksList, results);
            threads[i].start();
        }
    }

    void terminate() {
        for (Worker w : threads) {
            w.interrupt();
        }
    }

    @SuppressWarnings({"LoopConditionNotUpdatedInsideLoop", "StatementWithEmptyBody"})
    Object getNextResult() {
        while (results.isEmpty()) ;
        return results.poll();
    }

    void run(iTask<?> task) {
        synchronized (tasksList) {
            System.out.println("Got new task");
            tasksList.add(task);
            tasksList.notify();
        }
    }
}
