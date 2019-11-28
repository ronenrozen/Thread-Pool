import java.util.concurrent.BlockingQueue;

class Worker<T> extends Thread {

    private final BlockingQueue<iTask<T>> blockingQueue;
    private T results;
    private iTask<T> task;

    Worker(BlockingQueue<iTask<T>> blockingQueue) {
        this.blockingQueue = blockingQueue;
        this.task = null;
    }

    T getResults() {
        return results;
    }

    iTask getCurrentTask() { return this.task; }

    @Override
    public void run() {
        iTask<T> task;
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
            System.out.println("End of thread");
        } catch (Exception e) {
            throw new RuntimeException("Thread pool was interrupted: " + e.getMessage());
        }
    }
}
