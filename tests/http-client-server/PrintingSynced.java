import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class

public class PrintingSynced {

    public void printString(String msg) {
        synchronized (PrintingSynced.class) {
        	LocalDateTime ldtNow = LocalDateTime.now();
        	DateTimeFormatter fmtNow = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        	String formattedTime = ldtNow.format(fmtNow);
            System.out.print(formattedTime);
            System.out.print(" ");
            System.out.println(msg);
        }
    }

    public void printLabeledMsg(String label, String msg) {
        synchronized (PrintingSynced.class) {
        	LocalDateTime ldtNow = LocalDateTime.now();
        	DateTimeFormatter fmtNow = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        	String formattedTime = ldtNow.format(fmtNow);
            System.out.print(formattedTime);
            System.out.print(" ");
            System.out.print(label);
            System.out.print(": ");
            if (msg.length() < 1) System.out.println("<nil>");
            else System.out.println(msg);
        }
    }

    public void printStackTrace(Exception ee) {
        synchronized (PrintingSynced.class) {
            ee.printStackTrace();
        }
    }

}


