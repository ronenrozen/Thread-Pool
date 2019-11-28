public class MatrixMultiplicationTask implements iTask<Matrix> {

    private Matrix a;
    private Matrix b;

    public MatrixMultiplicationTask(Matrix a, Matrix b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Matrix call() throws Exception {
        return a.multiply(b);
    }
}
