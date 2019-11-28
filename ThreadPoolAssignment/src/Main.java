public class Main {

    /**
     * Some help for those who implement the thread pool class
     * The thread pool should sync on the queue and notify the queue whenever there's a new task
     * Don't forget to join the threads when you invoke them, or else the returning results will not be correct
     * This main should be deleted(!)
     */
    public static void main(String[] args) {
        int number = 10;
        Matrix[] matrices = new Matrix[number];
        for (int i = 0; i < number; i++) {
            matrices[i] = new Matrix(2, 10);
        }
        ThreadPool threadPool = new ThreadPool(5);
        do {
            if (matrices.length == 1) {
                matrices[0].print();
            } else if (matrices.length % 2 == 0) {
                number = number / 2;
                Matrix[] newMatrices = new Matrix[number];
                for (int i = 0; i < matrices.length; i += 2) {
                    newMatrices[i / 2] = (Matrix) threadPool.run(new MatrixMultiplicationTask(matrices[i], matrices[i + 1]));
                }
                matrices = newMatrices;
            } else if (matrices.length % 2 != 0) {
                number = number / 2 + 1;
                Matrix[] newMatrices = new Matrix[number];
                for (int i = 0; i < matrices.length - 1; i += 2) {
                    newMatrices[i / 2] = (Matrix) threadPool.run(new MatrixMultiplicationTask(matrices[i], matrices[i + 1]));
                }
                newMatrices[number - 1] = matrices[(number - 1) * 2];
                matrices = newMatrices;
            }
        } while (matrices.length > 1);
    }
}
