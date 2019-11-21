import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    /**
     * Some help for those who implement the thread pool class
     * The thread pool should sync on the queue and notify the queue whenever there's a new task
     * Don't forget to join the threads when you invoke them, or else the returning results will not be correct
     * This main should be deleted(!)
     */
    public static void main(String[] args) {
        BlockingQueue<Callable<double[]>> queue = new LinkedBlockingQueue<>();

        Worker worker = new Worker(queue);
        worker.start();
        synchronized (queue) {
            queue.add(() -> {
                System.out.println("In Task");
                double[] dummy = new double[1];
                dummy[0] = 1.0;
                System.out.println("returning: " + dummy[0]);
                return dummy;
            });
            queue.notify();
        }
        try {
            worker.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double[] results = worker.getResults();
        System.out.println("results: " + results[0]);
    }
}
