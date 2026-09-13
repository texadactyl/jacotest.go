import java.util.logging.Logger;
import java.util.logging.Level;

public class main {

	static int MAX_RECORDS = 3;
    static Logger logger = Logger.getLogger("Fred");
    
    public static void main(String[] args) {
    
        String msg;
 
        for (int ii = 0; ii < MAX_RECORDS; ii++) {
            msg = String.format("Message #%d", ii + 1);
            logger.log(Level.INFO, msg);
        }
        
        Checkers.theEnd(0);
    }

}
