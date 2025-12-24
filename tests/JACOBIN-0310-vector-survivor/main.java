
import java.util.Vector;

public class main {

    // TODO: needs a lot of work!
    
    public static void main(String[] args) {
        Integer[] vintIntegers = {1, 2, 3, 4, 5, 6};
        Vector<Integer> vint = new Vector<Integer>(1, 1);
        for (int ii = 0; ii < vintIntegers.length; ii++) {
            vint.add(vintIntegers[ii]);
        }
        Checkers.theEnd(0);
   }
}
