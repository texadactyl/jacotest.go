import java.util.Properties;

public class main {

    public static void main(String args[]) {
    
        System.out.println("Properties");
        int errorCount = 0;
        String expected;
        String observed;
        String[] keys = {
            "alpha",
            "beta",
            "gamma",
            "delta",
            "epsilon",
            "zeta",
            "eta",
            "theta"
        };
        String[] values = {
            "Ganymede",
            "Callisto",
            "Io",
            "Europa",
            "Amalthea",
            "Himalia",
            "Thebe",
            "Elara"
        };
        
        Properties props = new Properties();
        for (int ix = 0; ix < keys.length; ix++)
            props.setProperty(keys[ix], values[ix]);

        observed = props.toString();
        System.out.println(observed);
        
        observed = props.getProperty("zeta");
        expected = "Himalia";
        errorCount += Checkers.checker("props.getProperty(\"zeta\")", expected, observed);
        
        observed = props.getProperty("zeta", "apple");
        expected = "Himalia";
        errorCount += Checkers.checker("props.getProperty(\"zeta\")", expected, observed);
        
        observed = props.getProperty("missing", "orange");
        expected = "orange";
        errorCount += Checkers.checker("props.getProperty(\"missing\")", expected, observed);
       
        observed = props.getProperty("missing");
        if (observed == null) {
            System.out.println("main ok, props.getProperty(\"missing\") returned null");
        } else {
            errorCount += 1;
            System.out.println("main *** ERROR, props.getProperty(\"missing\") should have returned null");
        }
       
        props.remove("gamma");
        observed = props.getProperty("gamma");
        if (observed == null) {
            System.out.println("main ok, props.getProperty(\"gamma\") returned null");
        } else {
            errorCount += 1;
            System.out.println("main *** ERROR, props.getProperty(\"gamma\") should have returned null");
        }
        
        props.setProperty("iota", "Pasiphae");
        observed = props.getProperty("iota", "apple");
        expected = "Pasiphae";
        errorCount += Checkers.checker("props.set/getProperty(\"iota\", \"Pasiphae\")", expected, observed);
       
        props.setProperty("iota", "Carme");
        observed = props.getProperty("iota", "apple");
        expected = "Carme";
        errorCount += Checkers.checker("props.set/getProperty(\"iota\", \"Carme\")", expected, observed);
        
        observed = String.format("%02d", props.size());
        expected = "08";
        errorCount += Checkers.checker("props.size()", expected, observed);

        props.clear();
        observed = String.format("%02d", props.size());
        expected = "00";
        errorCount += Checkers.checker("props.size() after clear()", expected, observed);
       
        Checkers.theEnd(errorCount);

    }
}
