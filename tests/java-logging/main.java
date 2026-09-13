import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class main {

	static int MAX_RECORDS = 3;
    static Logger logger = Logger.getLogger("Fred");
    
    public static void main(String[] args) {
    
        String jvmPgmName = jj._getProgramName();
        String pathOutputFile = String.format("./logging.%s.txt", jvmPgmName);
        System.out.printf("Output file: %s\n", pathOutputFile);
        logger.setUseParentHandlers(false);
 
        try {
        
		    // Add off-the-shelf console handler.
		    ConsoleHandler consoleHandler = new ConsoleHandler();
            logger.addHandler(consoleHandler);

            // Add off-the-shelf file handler.
            FileHandler fileHandler = new FileHandler(pathOutputFile);
            logger.addHandler(fileHandler);
            
            // Customize the formatter for the file handler.
            fileHandler.setFormatter(new MyFormatter());
            logger.setLevel(Level.CONFIG);
            logger.config("\"The first casualty when war comes is truth.\" -Hiram Johnson in 1918"); 
            
            // Add custom filter to file handler.
            logger.setLevel(Level.ALL);
            fileHandler.setFilter(new MyFilter());
            
            // Begin logging.
            String msg;
            for (int ii = 0; ii < MAX_RECORDS; ii++) {
                //logging messages
                msg = String.format("Message #%d", ii + 1);
                logger.log(Level.INFO, msg);
            }
            
            
        } catch (SecurityException | IOException ee) {
            ee.printStackTrace();
        } finally {
            for (Handler handler : logger.getHandlers()) {
                handler.close();
            }
        }
        
        Checkers.theEnd(0);
    }

}

// Class jj in case we are executed by the OpenJDK JVM.
class jj {

    public static String _getProgramName() {
        System.out.println("J-class function _getProgramName (not Jacobin)");
        return "java";
    }
   
}

