import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

class Worker<T> extends Thread {

    private final BlockingQueue<Callable<T>> blockingQueue;
    private T results;
    private Callable<T> task;

    Worker(BlockingQueue<Callable<T>> blockingQueue) {
        this.blockingQueue = blockingQueue;
        this.task = null;
    }

    T getResults() {
        return results;
    }

    Callable getCurrentTask() { return this.task; }

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
            this.task=task;
        }

        try {
            assert task != null;
            T res = task.call();
            synchronized (this){
                results = res;
                this.wait();
            }
        } catch (Exception e) {
            throw new RuntimeException("Thread pool was interrupted: " + e.getMessage());
        }
    }
}