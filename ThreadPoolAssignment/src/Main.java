import java.util.concurrent.Callable;

public class Main {

    /**
     * Some help for those who implement the thread pool class
     * The thread pool should sync on the queue and notify the queue whenever there's a new task
     * Don't forget to join the threads when you invoke them, or else the returning results will not be correct
     * This main should be deleted(!)
     */
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(2);
        Callable[] tasks = new Callable[]{
                () -> {
                    double[] dummy1 = {1.0};
                    System.out.println("returning dummy1: " + dummy1[0]);
                    return dummy1;
                },
                () -> {
                    double[] dummy2 = {2.0};
                    System.out.println("returning dummy2: " + dummy2[0]);
                    return dummy2;
                }
        };

        for (Callable task : tasks) {
            double[] results = threadPool.run(task);
            System.out.println("results: " + results[0]);
        }
    }
}