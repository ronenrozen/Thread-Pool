import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

class Worker<T> extends Thread {

    private final BlockingQueue<Callable<T>> blockingQueue;
    private T results;

    Worker(BlockingQueue<Callable<T>> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    T getResults() {
        return results;
    }

    @Override
    public void run() {
        Callable<T> task;
        synchronized (blockingQueue) {
            while (blockingQueue.isEmpty()) {
                try {
                    System.out.println("queue is empty, waiting");
                    blockingQueue.wait(); // Thread Pool should notify when something is added to queue
                } catch (InterruptedException e) {
                    System.err.println("Queue waiting was interrupted: " + e.getMessage());
                }
            }
            System.out.println("queue received task");
            task = blockingQueue.poll();
        }

        try {
            assert task != null;
            results = task.call();
        } catch (Exception e) {
            throw new RuntimeException("Thread pool was interrupted: " + e.getMessage());
        }
    }
}
