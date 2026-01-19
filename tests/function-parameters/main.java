public class main {

    // Method that accepts a function as a parameter
    public static int processNumber(int num, Operation operation) {
        int result = operation.execute(num);
        return result;
    }
    
    // Interface to define the contract
    interface Operation {
        int execute(int x);
    }
    
    // First function implementation
    static class DoubleOperation implements Operation {
        public int execute(int x) {
            return x * 2;
        }
    }
    
    // Second function implementation
    static class SquareOperation implements Operation {
        public int execute(int x) {
            return x * x;
        }
    }
    
    public static void main(String[] args) {
        int errorCount = 0;
        int number = 5;
        int result;
        
        // Pass the first function
        result = processNumber(number, new DoubleOperation());  // Output: Result: 10
        errorCount += Checkers.checker("DoubleOperation(5)", 10, result);
        
        // Pass the second function
        result = processNumber(number, new SquareOperation());  // Output: Result: 25
        errorCount += Checkers.checker("SquareOperation(5)", 25, result);
        
        Checkers.theEnd(errorCount);
    }
}
