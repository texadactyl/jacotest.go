// hacked from jacotest case desi

public class main {

    private static int checkElement(int[][] matrix, int row, int col, int value) {
        if (matrix[row][col] == value) {
            System.out.printf("checkElement ok, row=%d, col=%d, value=%d\n", row, col, value);
            return 0;
        }
        System.out.printf("checkElement *** ERROR, row=%d, col=%d, expected=%d, observed=%d\n", row, col, value, matrix[row][col]);
        return 1;
    }

	public static void main(String args[]) {
		
		int nrows = 6;
		int ncols = 8;
		int errorCount = 0;
		
        int[][] matrix = new int[nrows][ncols];

		int vector[] = new int[ncols];
		for(int ix = 0 ; ix < vector.length ; ix++) {
			vector[ix] = ix;
		}

        for (int row = 0; row < nrows; row++) {
            //matrix[row] = vector; // a reference assignment, not a deep copy
            System.arraycopy(vector, 0, matrix[row], 0, ncols); // deep copy
            for (int col = 0; col < ncols; col++)
                matrix[row][col] += row * 100;
        }

		errorCount += checkElement(matrix, 2, 3, 203);
		errorCount += checkElement(matrix, 5, 4, 504);
		
		Checkers.theEnd(errorCount);
	}

}
