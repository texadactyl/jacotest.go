// hacked from https://docs.oracle.com/javase%2Ftutorial%2Fuiswing%2F%2F/examples/components/SimpleTableDemoProject/src/components/SimpleTableDemo.java
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class main {

    public static void main(String[] args) {
    	String display = System.getenv("DISPLAY");
    	if (display.length() > 0) {
        	System.setProperty("java.awt.headless", "false");
        } else {
        	System.setProperty("java.awt.headless", "true");
        }
        SimpleTableDemo std = new SimpleTableDemo();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	while (! std.finished) {
                	std.createAndProcJframe();
                }
                assert (std.errCount == 0);
                	
                System.exit(0);
            }
        });
    }
    
}
