import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {


    @org.junit.jupiter.api.Test
    void multiplyMatrixByItSelf4x4() {
        System.out.println("multiplyMatrixByItSelf4x4 TEST");
        Matrix a = new Matrix(4);
        int[] row0 = {1,2,3,4};
        int[] row1 = {5,6,7,8};
        int[] row2 = {9,10,11,12};
        int[] row3 = {13,14,15,16};

        a.setRow(0, row0);
        a.setRow(1, row1);
        a.setRow(2, row2);
        a.setRow(3, row3);


        Matrix expectedResult = new Matrix(4);

        int[] eRow0 = {90,100,110,120};
        int[] eRow1 = {202,228,254,280};
        int[] eRow2 = {314,356,398,440};
        int[] eRow3 = {426,484,542,600};

        expectedResult.setRow(0, eRow0);
        expectedResult.setRow(1, eRow1);
        expectedResult.setRow(2, eRow2);
        expectedResult.setRow(3, eRow3);

        Matrix b = a.multiply(a);
        int dim = a.getDim();
        b.print();
        if (!(Matrix.isEqual(b, expectedResult)))
            fail("Matrices aren't equal");

        System.out.println("multiply by self 4x4 ran successfully");
    }

    @org.junit.jupiter.api.Test
    void multiplyMatrixUsing4ThreadsWithThreadPool() {
        System.out.println("multiplyMatrixUsing4ThreadsWithThreadPool TEST");
        ThreadPool tp = new ThreadPool(4);
        Matrix[] matrices = new  Matrix[2];
        matrices[0] = new Matrix(5);
        matrices[1] = new Matrix(5);

        int[][] mat1 = {
                {1,2,3,4,5},
                {6,7,8,9,10},
                {11,12,13,14,15},
                {16,17,18,19,20},
                {21,22,23,24,25}};

        for (int i = 0; i < 5 ; ++i)
            matrices[0].setRow(i,mat1[i]);

        int[][] mat2 = {
                {2,3,4,5,6},
                {7,8,9,10,11},
                {12,13,14,15,16},
                {17,18,19,20,21},
                {22,23,24,25,26}};

        for (int i = 0; i < 5 ; ++i)
            matrices[1].setRow(i,mat2[i]);

        Matrix expectedResult = new Matrix(5);
        int[][] mat3 = {
                {230,245,260,275,290},
                {530,570,610,650,690},
                {830,895,960,1025,1090},
                {1130,1220,1310,1400,1490},
                {1430,1545,1660,1775,1890}};

        for (int i = 0; i < 5 ; ++i)
            expectedResult.setRow(i,mat3[i]);

        while (matrices.length > 1) {
            matrices = Main.multiplyMatrices(matrices, tp, matrices.length % 2 == 0);
        }
        matrices[0].print();

        tp.terminate();
        if (!(Matrix.isEqual(expectedResult, matrices[0])))
            fail("Matrices aren't equal");
        else {
            System.out.println("Matrix multiplication 5x5 with ThreadPool & 4 thread test success");
        }
    }

    @org.junit.jupiter.api.Test
    void multiply5x5Matrices() {
        System.out.println("multiply5x5Matrices TEST");
        Matrix[] matrices = new  Matrix[2];
        matrices[0] = new Matrix(5);
        matrices[1] = new Matrix(5);

        int[][] mat1 = {
                {1,2,3,4,5},
                {6,7,8,9,10},
                {11,12,13,14,15},
                {16,17,18,19,20},
                {21,22,23,24,25}};

        for (int i = 0; i < 5 ; ++i)
            matrices[0].setRow(i,mat1[i]);

        int[][] mat2 = {
                {2,3,4,5,6},
                {7,8,9,10,11},
                {12,13,14,15,16},
                {17,18,19,20,21},
                {22,23,24,25,26}};

        for (int i = 0; i < 5 ; ++i)
            matrices[1].setRow(i,mat2[i]);

        Matrix expectedResult = new Matrix(5);
        int[][] mat3 = {
                {230,245,260,275,290},
                {530,570,610,650,690},
                {830,895,960,1025,1090},
                {1130,1220,1310,1400,1490},
                {1430,1545,1660,1775,1890}};

        for (int i = 0; i < 5 ; ++i)
            expectedResult.setRow(i,mat3[i]);

        Matrix result = matrices[0].multiply(matrices[1]);
        result.print();

        if (!(Matrix.isEqual(expectedResult, result)))
            fail("Matrices aren't equal");
        else {
            System.out.println("multiply5x5Matrices test success");
        }
    }

    @org.junit.jupiter.api.Test
    void multiplyDifferentDimensionMatricesGivesException() {

        Matrix a = new Matrix(5);
        int[][] mat1 = {
                {1,2,3,4,5},
                {6,7,8,9,10},
                {11,12,13,14,15},
                {16,17,18,19,20},
                {21,22,23,24,25}};

        for (int i = 0; i < 5; ++i)
            a.setRow(i, mat1[i]);

        Matrix b = new Matrix(3);
        int[][] mat2 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};

        for (int i = 0;  i < 3; ++i)
            b.setRow(i, mat2[i]);

        try {
            a.multiply(b);
            fail("Exception not reached multiplying 3x3 x 5x5");
        }catch (Exception e) {
            System.out.println("Exception reached!");
        }

    }

}
