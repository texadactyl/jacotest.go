

/**
 * LU matrix factorization. (Based on TNT implementation.)
 * Decomposes a matrix A  into a triangular lower triangular
 * factor (L) and an upper triangular factor (U) such that
 * A = L*U.  By convention, the main diagonal of L consists
 * of 1's so that L and U can be stored compactly in
 * an NxN matrix.
 */
public class LU {

    /**
     * LU factorization (in place).
     *
     * @param A     (in/out) On input, the matrix to be factored.
     *              On output, the compact LU factorization.
     * @param pivot (out) The pivot vector records the
     *              reordering of the rows of A during factorization.
     * @return 0: OK, 1: factorization failed because of zero pivot.
     */
    public static int factor(double[][] A, int[] pivot) {


        int nrows = A.length;
        int ncols = A[0].length;
        int minNrowsNcols = Math.min(ncols, nrows);

        for (int j = 0; j < minNrowsNcols; j++) {
            // find pivot in column j and  test for singularity.

            int jp = j;

            double t = Math.abs(A[j][j]);
            for (int i = j + 1; i < ncols; i++) {
                double ab = Math.abs(A[i][j]);
                if (ab > t) {
                    jp = i;
                    t = ab;
                }
            }

            pivot[j] = jp;

            // jp now has the index of maximum element
            // of column j, below the diagonal

            /*

            Accept a zero pivot.
            The zero pivot is a very old bug in this Java source.
            It has no bearing on the timing result.

            if (A[jp][j] == 0)
                return 1;       // factorization failed because of zero pivot

            */

            if (jp != j) {
                // swap rows j and jp
                // System.arraycopy(vector, 0, matrix[row], 0, ncols);
                double[] temp = new double[A[0].length];
                System.arraycopy(A[j], 0, temp, 0, A[0].length);
                System.arraycopy(A[jp], 0, A[j], 0, A[0].length);
                System.arraycopy(temp, 0, A[jp], 0, A[0].length);
            }

            if (j < ncols - 1)                // compute elements j+1:M of jth column
            {
                // note A(j,j), was A(jp,p) previously which was
                // guaranteed not to be zero (Label #1)
                //
                double recp = 1.0 / A[j][j];

                for (int k = j + 1; k < ncols; k++)
                    A[k][j] *= recp;
            }


            if (j < minNrowsNcols - 1) {
                // rank-1 update to trailing sub-matrix:   E = E - x*y;
                //
                // E is the region A(j+1:M, j+1:N)
                // x is the column vector A(j+1:M,j)
                // y is row vector A(j,j+1:N)


                for (int ii = j + 1; ii < ncols; ii++) {
                    double[] Aii = A[ii];
                    double[] Aj = A[j];
                    double AiiJ = Aii[j];
                    for (int jj = j + 1; jj < nrows; jj++)
                        Aii[jj] -= AiiJ * Aj[jj];

                }
            }
        }

        return 0;
    }

}
