import java.util.Properties;

public class main {

    static int errorCounter = 0;

    public static void main(String[] args) {

        int errorCounter= 0;
        Properties sysprops = System.getProperties();       
        //System.out.println(sysprops.toString());
        
        String[] keys = {"file.encoding", "java.library.path", "java.vm.name", "os.version", "user.timezone"};
        for (String key : keys) {
            String value1 = sysprops.getProperty(key);
            String value2 = System.getProperty(key);
            errorCounter += check(key, value1, value2);
        }
        
        String curValue = System.getProperty("file.encoding");
        String oldValue = System.clearProperty("file.encoding");
        errorCounter += check("file.encoding after getProperty + clearProperty", curValue, oldValue);
        curValue = System.getProperty("file.encoding");
        if (curValue != null) {
            errorCounter += 1;
            System.out.println("*** ERROR, get + clear + get should have returned a null string");
        }
        
        Properties userprops = new Properties();
        userprops.setProperty("color", "orange");
        userprops.setProperty("sound", "soft");
        System.setProperties(userprops);
        curValue = System.getProperty("color");
        errorCounter += check("color", "orange", curValue);
        
        curValue = System.getProperty("plant", "default");
        errorCounter += check("plant", "default", curValue);

        // Final assertion for errorCounter
        if (errorCounter > 0)
            System.out.printf("Error count = %d\n", errorCounter);
        assert errorCounter == 0;
        System.out.println("Success!");
    }

    static int check(String key, String expected, String observed) {
        if (expected == null && observed == null) {
            System.out.printf("%s: OK, both expected & observed are null\n", key);
            return 0;
        }
        if (expected == null) {
            System.out.printf("%s: *** ERROR, expected is null, observed: %s\n", key, observed);
            return 1;
        }
        if (observed == null) {
            System.out.printf("%s: *** ERROR, expected: %s, observed is null\n", key, expected);
            return 1;
        }
        if (expected.equals(observed)) {
            System.out.printf("%s: %s OK\n", key, expected);
            return 0;
        } else {
            System.out.printf("%s :*** ERROR", key);
            System.out.printf(", Expected: %s", expected);
            System.out.printf(", Observed: %s\n", observed);
            return 1;
        }
    }

}

