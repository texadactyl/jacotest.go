import java.util.Properties;

public class main {

    private static int checker(String label, String expected, String observed) {
        if (expected.equals(observed)) {
            System.out.printf("checker ok, %s: expected(%s) = observed(%s)\n", label, expected, observed);
            return 0;
        }
        System.out.printf("checker *** ERROR, %s: expected(%s) != observed(%s)\n", label, expected, observed);
        return 1;
    }

    public static void main(String args[]) {
    
        System.out.println("Properties");
        int errorCounter = 0;
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
        errorCounter += checker("props.getProperty(\"zeta\")", expected, observed);
        
        observed = props.getProperty("zeta", "apple");
        expected = "Himalia";
        errorCounter += checker("props.getProperty(\"zeta\")", expected, observed);
        
        observed = props.getProperty("missing", "orange");
        expected = "orange";
        errorCounter += checker("props.getProperty(\"missing\")", expected, observed);
       
        observed = props.getProperty("missing");
        if (observed == null) {
            System.out.println("main ok, props.getProperty(\"missing\") returned null");
        } else {
            errorCounter += 1;
            System.out.println("main *** ERROR, props.getProperty(\"missing\") should have returned null");
        }
       
        props.remove("gamma");
        observed = props.getProperty("gamma");
        if (observed == null) {
            System.out.println("main ok, props.getProperty(\"gamma\") returned null");
        } else {
            errorCounter += 1;
            System.out.println("main *** ERROR, props.getProperty(\"gamma\") should have returned null");
        }
        
        props.setProperty("iota", "Pasiphae");
        observed = props.getProperty("iota", "apple");
        expected = "Pasiphae";
        errorCounter += checker("props.set/getProperty(\"iota\", \"Pasiphae\")", expected, observed);
       
        props.setProperty("iota", "Carme");
        observed = props.getProperty("iota", "apple");
        expected = "Carme";
        errorCounter += checker("props.set/getProperty(\"iota\", \"Carme\")", expected, observed);
        
        observed = String.format("%02d", props.size());
        expected = "08";
        errorCounter += checker("props.size()", expected, observed);

        props.clear();
        observed = String.format("%02d", props.size());
        expected = "00";
        errorCounter += checker("props.size() after clear()", expected, observed);
       
        assert errorCounter == 0;
        System.out.println("Success!");

    }
}
