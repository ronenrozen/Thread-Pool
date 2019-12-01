import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    private static int getNumberOfThreads() {
        while (true) {
            System.out.println("Please enter number of threads");
            int threadNumber = sc.nextInt();
            if (threadNumber < 2 || threadNumber > 20) {
                System.out.println("Number of thread needs to be between 2 and 20");
                continue;
            }
            return threadNumber;
        }
    }

    private static int getNumberOfMatrices() {
        while (true) {
            System.out.println("Please enter number of matrices");
            int matricesNumber = sc.nextInt();
            if (matricesNumber < 2) {
                System.out.println("Number of matrices needs to be at list 2");
                continue;
            }
            return matricesNumber;
        }
    }

    private static int getDim() {
        while (true) {
            System.out.println("Please enter dim of matrices");
            int dim = sc.nextInt();
            if (dim < 1) {
                System.out.println("Dim can't be non-positive");
                continue;
            }
            return dim;
        }
    }

    private static Matrix[] getMatrices(int matricesNumber, int dim) {
        Matrix[] matrices = new Matrix[matricesNumber];
        for (int i = 0; i < matrices.length; i++) {
            matrices[i] = new Matrix(dim, 10);
        }
        return matrices;
    }

    protected static Matrix[] multiplyMatrices(Matrix[] oldMatrices, ThreadPool threadPool, boolean isEven) {
        int newLength = isEven ? oldMatrices.length / 2 : oldMatrices.length / 2 + 1;
        Matrix[] newMatrices = new Matrix[newLength];
        int limit = isEven ? oldMatrices.length : oldMatrices.length - 1;
        for (int i = 0; i < limit; i += 2) {
            threadPool.run(new MatrixMultiplicationTask(oldMatrices[i], oldMatrices[i + 1]));

        }

        for (int i = 0; i < limit; i += 2) {
            newMatrices[i / 2] = (Matrix) threadPool.getNextResult();
        }

        if (!isEven) {
            newMatrices[newLength - 1] = oldMatrices[(newLength - 1) * 2];
        }
        return newMatrices;
    }

    public static void main(String[] args) {
        int threadNumber = getNumberOfThreads();
        int matricesNumber = getNumberOfMatrices();
        int dim = getDim();

        Matrix[] matrices = getMatrices(matricesNumber, dim);
        ThreadPool threadPool = new ThreadPool(threadNumber);

        while (matrices.length > 1) {
            matrices = multiplyMatrices(matrices, threadPool, matrices.length % 2 == 0);
        }

        matrices[0].print();
        threadPool.terminate();
    }
}
