public class Helpers {

    public int isItTrue(String label, boolean bool) {
        if (bool) {
            System.out.printf("Success :: %s\n", label);
            return 0;
        }
        System.out.printf("*** ERROR, %s\n", label);
        return 1;
    }

    public int checker(String label, String expected, String observed) {
        if (expected.equals(observed)) {
            System.out.printf("Success :: %s, expected: %s\n", label, expected);
            return 0;
        }
        System.out.printf("*** ERROR, %s, expected: %s, observed: %s\n", label, expected, observed);
        return 1;
    }
    
    public void byebye(int errorCount) {
    	System.out.printf("Number of errors = %d\n", errorCount);
        assert (errorCount == 0);
    }

}
