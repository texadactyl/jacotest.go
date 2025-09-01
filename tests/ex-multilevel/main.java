public class main {
    public static void main(String[] args) {
        int ii;
        int one = 1;
        int zero = 0;
        try {
            ii = one / zero;
        } catch (ArithmeticException ex1) {
            System.out.println("Caught ArithmeticException level #1");
            try {
                ii = one / zero;
            } catch (ArithmeticException ex2) {
                System.out.println("Caught ArithmeticException level #2");
                try {
                    ii = one / zero;
                } catch (ArithmeticException ex3) {
                    System.out.println("Caught ArithmeticException level #3");
                }
                finally {
                    System.out.println("Finally level #3");
                }
            } // End of catch (ArithmeticException ex2)
            finally {
                System.out.println("Finally level #2");
            }
        } // End of catch (ArithmeticException ex1)
        finally {
            System.out.println("Finally level #1");
        }
        
        Checkers.theEnd(0);

    }
}
