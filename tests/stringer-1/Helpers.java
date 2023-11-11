public class Helpers {

    public int isItTrue(String label, boolean bool) {
        if (bool) {
            System.out.printf("Success :: %s\n", label);
            return 0;
        }
        System.out.printf("*** ERROR, %s\n", label);
        return 1;
    }
    
    public void byebye(int errorCount) {
    	System.out.printf("Number of errors = %d\n", errorCount);
        assert (errorCount == 0);
    }

}
