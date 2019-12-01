import java.util.Random;
import java.util.stream.IntStream;

public class Matrix {

    private int[][] matrix;
    private int dim;

    public Matrix(int dim, int randRange) {
        this.dim = dim;
        this.matrix = new int[dim][dim];
        IntStream.range(0, dim).forEach(i ->
                IntStream.range(0, dim).forEach(j ->
                        matrix[i][j] = new Random().nextInt(randRange)));
    }

    public Matrix(int dim) {
        this.dim = dim;
        this.matrix = new int[dim][dim];
    }

    public int[] getRow(int i) {
        return matrix[i];
    }

    public int[] getCol(int j) {
        int[] col = new int[this.dim];
        IntStream.range(0, dim).forEach(i -> col[i] = matrix[i][j]);
        return col;
    }

    public int getDim() {
        return dim;
    }

    public void setRow(int i, int[] row) {
        this.matrix[i] = row;
    }

    private int res = 0;

    public Matrix multiply(Matrix b) {
        Matrix result = new Matrix(dim);
        IntStream.range(0, dim).forEach(i -> {
            int[] new_row = new int[dim];
            IntStream.range(0, dim).forEach(j -> {
                int[] col = b.getCol(j);
                int[] row = this.getRow(i);
                IntStream.range(0, dim).forEach(k -> new_row[j] += col[k] * row[k]);
            });
            result.setRow(i, new_row);
        });
        return result;
    }
    
    public static boolean isEqual(Matrix a, Matrix b) {
        if (a.getDim() == b.getDim()) {
            int dim = a.getDim();
            for (int i = 0; i < dim; ++i) {
                int[] rowA = a.getRow(i);
                int[] rowB = b.getRow(i);
                for (int j = 0; j < dim; ++j)
                    if (rowA[j] != rowB[j])
                        return false;
            }
            return true;
        }
        else
            return false;
    }

    public void print() {
        System.out.print("[");
        IntStream.range(0, dim - 1).forEach(i -> printRow(i, true, "\n "));
        printRow(dim - 1, false, "]");
        System.out.println();
    }

    void printRow(int i, boolean withComma, String finisher) {
        System.out.print("[");
        IntStream.range(0, dim - 1).forEach(j -> System.out.print(matrix[i][j] + ", "));
        System.out.print(matrix[i][dim - 1] + "]" + (withComma ? "," : ""));
        System.out.print(finisher);
    }
}
