import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

class Worker extends Thread {

    private final BlockingQueue<Callable<double[]>> blockingQueue;
    private double[] results = {0.0};

    Worker(BlockingQueue<Callable<double[]>> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    double[] getResults() {
        return results;
    }

    @Override
    public void run() {
        Callable<double[]> task;
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
            System.out.println("got results: " + results[0]);
        } catch (Exception e) {
            throw new RuntimeException("Thread pool was interrupted: " + e.getMessage());
        }
    }
}
