import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

public class main {

	static int MAX_RECORDS = 10;
	static String PATH_CFG_FILE = "./logging.cfg";

    static Logger logger = Logger.getLogger(main.class.getName());
    
    public static void main(String[] args) {
 
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream(PATH_CFG_FILE));
        } catch (SecurityException | IOException ee) {
            ee.printStackTrace();
        }

		// Add off-the-shelf console handler.
        logger.addHandler(new ConsoleHandler());

        // Add custom Stream handler
        logger.addHandler(new MyHandler());

        try {
        
            // Instantiate a File handler.
            Handler fileHandler = new FileHandler();
            logger.addHandler(fileHandler);
            
            // Show logging configuration.
            fileHandler.setFormatter(new XMLFormatter());
            logger.setLevel(Level.CONFIG);
            logger.config("\"The first casualty when war comes is truth.\" -Hiram Johnson in 1918"); 
            
            // Change formatter for the file handler.
            fileHandler.setFormatter(new MyFormatter());
            
            // Add custom filter to file handler.
            logger.setLevel(Level.ALL);
            fileHandler.setFilter(new MyFilter());
            
            // Let's get logging!
            String msg;
            for (int ii = 0; ii < MAX_RECORDS; ii++) {
                //logging messages
                msg = String.format("Message #%d", ii + 1);
                logger.log(Level.INFO, msg);
            }
            
        } catch (SecurityException | IOException ee) {
            ee.printStackTrace();
        }
    }

}
