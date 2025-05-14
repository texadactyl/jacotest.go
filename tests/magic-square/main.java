// MagicSquare - odd number of rows/columns

public class main {
    private static int[][] cells;
    
    private static void init(int n) {
        // Populate the square.
        cells = new int[n][];      
        for (int ix = 0; ix < n; ++ix) {
            cells[ix] = new int[n];
        }
    }
    
    private static int getCell(int row, int col) {
        return cells[row][col];
    }
    
    private static void setCell(int row, int col, int value) {
        cells[row][col] = value;
    }
    
    
    private static void checker(int n) {
        int expectedValue = ((n * n * n) + n) / 2;
        System.out.printf("Expected value across rows, columns, and diagonals = %d\n", expectedValue);
        int sum;

        // Diagonal NW to SE.
        sum = 0;
        for (int row = 0; row < n; row++) {
            sum += cells[row][row];
        }
        if (sum != expectedValue) 
            throw new IllegalStateException(String.format("NW diagonal sum expected %d, observed %d", expectedValue, sum));
        
        // Diagonal SW to NE.
        sum = 0;
        for (int row = 0; row < n; row++) {
            sum += cells[row][n - row - 1];
        }
        if (sum != expectedValue) 
            throw new IllegalStateException(String.format("SW diagonal sum expected %d, observed %d", expectedValue, sum));

        // Rows.
        for (int row = 0; row < n; row++) {
            sum = 0;
            for (int col = 0; col < n; col++) {
                sum += cells[row][col];
            }
            if (sum != expectedValue) 
                throw new IllegalStateException(String.format("Row %d sum expected %d, observed %d", row, expectedValue, sum));
        }

        // Columns.
        for (int col = 0; col < n; col++) {
            sum = 0;
            for (int row = 0; row < n; row++) {
                sum += cells[row][col];
            }
            if (sum != expectedValue) 
                throw new IllegalStateException(String.format("Column %d sum expected %d, observed %d", col, expectedValue, sum));
        }
    }
    
    public static void main(String[] args) {
    
        System.out.println("Magic Square 13x13");
    
        int n = 13;
    
        init(n);
    
        // Firstly, let’s place our first number. This is in the center cell on the top row.
        int y = 0;  
        int x = (n - 1) / 2;
        setCell(x, y, 1);
        
        // Loop over all of the other numbers, placing each one in turn.
        for (int number = 2; number <= n * n; ++number) {
        
            // To start with, we’ll attempt to move up and right, wrapping around as necessary.
            int nextX = x + 1; // right
            if (nextX == n) {
                nextX = 0;     // wrap around
            }
            int nextY = y - 1; // up
            if (nextY == -1) {
                nextY = n - 1; // wrap around
            }
            
            // Handle the case when this next cell is already occupied.
            if (getCell(nextX, nextY) != 0) {
                nextX = x;

                nextY = y + 1;
                if (nextY == n) {
                    nextY = 0; // wrap around
                }
            }

            // Put current number in the selected cell.
            setCell(nextX, nextY, number);

            x = nextX;
            y = nextY;
        }
        
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                System.out.printf("%5d   ", getCell(row, col));
            }
            System.out.println();
        }
        
        checker(n);
        System.out.println("Success!");

    }   
}

