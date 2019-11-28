import java.util.Objects;
import java.util.concurrent.BlockingQueue;

class Worker<T> extends Thread {

    private final BlockingQueue<iTask<T>> blockingQueue;
    private final BlockingQueue<T> resultQueue;

    Worker(BlockingQueue<iTask<T>> blockingQueue, BlockingQueue<T> resultQueue) {
        this.blockingQueue = blockingQueue;
        this.resultQueue = resultQueue;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (blockingQueue) {
                while (blockingQueue.isEmpty()) {
                    try {
                        System.out.println("queue is empty, waiting");
                        blockingQueue.wait(); // Thread Pool should notify when something is added to queue
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }

            try {
                System.out.println("queue received task, " + this.getName() + " is handling it");
                resultQueue.add(Objects.requireNonNull(blockingQueue.poll()).call());
                System.out.println(this.getName() + " pushed result");
            } catch (Exception e) {
                return;
            }
        }
    }
}
