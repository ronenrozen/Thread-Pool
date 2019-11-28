import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class ThreadPool {
    private final BlockingQueue<iTask<?>> tasksList;
    private final Worker[] threads;

    ThreadPool(int numberOfThreads) {
        tasksList = new LinkedBlockingQueue<>();
        threads = new Worker[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Worker(tasksList);
            threads[i].start();
        }
    }

    Object run(iTask<?> task) {
        synchronized (tasksList) {
            tasksList.add(task);
            tasksList.notify();
        }
        while (true) {
            for (Worker w : threads) {
                try {
                    //noinspection SynchronizationOnLocalVariableOrMethodParameter
                    synchronized (w) {
                        if (w.getCurrentTask() != null && w.getCurrentTask().equals(task)) {
                            w.notify();
                            if (w.getResults() != null) {
                                System.out.println("Thread number " + w.getName() + " returns result");
                                return w.getResults();
                            }
                        }
                    }
                } catch (Exception e) {
                    //nothing
                }
            }
        }
    }
}
