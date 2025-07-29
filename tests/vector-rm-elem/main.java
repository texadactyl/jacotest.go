import java.util.Vector;

public class main {

    private static long addEmUp(Vector<Long> vecLong) {
        System.out.println("---------------------------");
        long sum = 0;
        for (long elem : vecLong) {
            sum += elem;
            System.out.printf(">> addEmUp  elem: %d, sum: %d\n", elem, sum);
        }
        return sum;
    }

    public static void main(String[] args) throws Exception {
    
        // Vector of 6 long values
        Long[] longArray = {1001l, 1002l, 1003l, 1004l, 1005l, 1006l};
        Vector<Long> vecLong = new Vector<Long>();
        for (Long LL : longArray) 
            vecLong.add(LL);
            
        long sum = addEmUp(vecLong);
 
        // Remove the 4th element.
        vecLong.removeElementAt(3);
        sum = addEmUp(vecLong);

        // Validate.
        long expected = 5017l;
        if (sum == expected)
            System.out.println("Success!");
        else {
            System.out.printf("*** Failed, sum expected: %d, observed: %d\n", expected, sum);
            System.exit(1);
        }
    }
}
