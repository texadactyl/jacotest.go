import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SimpleTableDemo extends JPanel {

	public boolean finished = false;
	public int errCount = 0;

    private Object[][] objTable = {
		{"Kathy", "Smith", "Snowboarding", 5, false},
		{"John", "Doe", "Rowing", 3, true},
		{"Sue", "Black", "Knitting", 2, false},
		{"Jane", "White", "Speed reading", 20, true},
		{"Joe", "Brown",  "Pool", 10, false}
    };

    private JTable jtable;

    public SimpleTableDemo() {
        super(new GridLayout(1,0));

        String[] columnNames = {"First Name",
                                "Last Name",
                                "Sport",
                                "# of Years",
                                "Vegetarian"};

        jtable = new JTable(this.objTable, columnNames);
        jtable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        jtable.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(jtable);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    public void printTableContents(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();
        

        System.out.println("Model dump: ");
        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
                if (model.getValueAt(i, j) != this.objTable[i][j]) {
                	System.out.print(" *** ERROR, does not match ");
                	System.out.print(this.objTable[i][j]);
                	++errCount;
                }
            }
            System.out.println();
        }
    }

    /**
     * Create the Jframe and process it.
     */
    public 	void createAndProcJframe() {
        //Create and set up the window.
        JFrame frame = new JFrame("SimpleTableDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        SimpleTableDemo newContentPane = new SimpleTableDemo();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(false);
        printTableContents(this.jtable);
        this.finished = true;
    }

}
