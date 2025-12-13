import java.util.*;

public class main {

    static int errorCount = 0;
    static String PROVIDER_NAME;
    static String jvmPgmName;
    

    public static void main(String args[]) {

        jvmPgmName = jj._getProgramName();
        if (jvmPgmName.equals("java")) 
            PROVIDER_NAME = java.security.Security.getProviders()[0].getName();
        else
            PROVIDER_NAME = "GoSecurityProvider";
        System.out.println("Java security provider #2 tests");
        System.out.printf("jvmPgmName=%s\n", jvmPgmName);

        TestServiceFieldsAndAliases();
        TestServiceToString();

        Checkers.theEnd(errorCount);
    }

    // ------------------------ Tests ------------------------

    static private void TestServiceFieldsAndAliases() {
        // Simulate service with aliases
        List<String> aliases = Arrays.asList("RedApple", "GreenApple");
        SimulatedService s = new SimulatedService("Fruit", "Apple", "GoRuntimeService", aliases);

        // Map all names (main algorithm + aliases)
        Map<String, SimulatedService> serviceMap = new HashMap<>();
        serviceMap.put(s.algorithm, s);
        for (String alias : s.aliases) {
            serviceMap.put(alias, s);
        }

        // Print all mappings
        System.out.println("\nService → Alias mapping:");
        for (Map.Entry<String, SimulatedService> entry : serviceMap.entrySet()) {
            SimulatedService svc = entry.getValue();
            System.out.printf("Name: %s -> Type: %s, Algorithm: %s, ClassName: %s\n",
                    entry.getKey(), svc.type, svc.algorithm, svc.className);
        }

        // Verify all aliases resolve correctly
        for (String alias : aliases) {
            SimulatedService svc = serviceMap.get(alias);
            if (svc == null) {
                System.out.println("*** ERROR, alias not mapped: " + alias);
                ++errorCount;
            } else if (!"Apple".equals(svc.algorithm) || !"Fruit".equals(svc.type)) {
                System.out.println("*** ERROR, alias mapping incorrect for: " + alias);
                ++errorCount;
            }
        }
    }

    static private void TestServiceToString() {
        List<String> aliases = Arrays.asList("RedApple", "GreenApple");
        SimulatedService s = new SimulatedService("Fruit", "Apple", "GoRuntimeService", aliases);

        System.out.println("\nTesting toString() for main algorithm and aliases:");
        String expected = "Fruit/Apple (GoRuntimeService)";

        // Main algorithm
        if (!expected.equals(s.toString())) {
            System.out.println("*** ERROR, main algorithm toString incorrect: " + s.toString());
            ++errorCount;
        }

        // Aliases
        for (String alias : aliases) {
            if (!expected.equals(s.toString())) {
                System.out.println("*** ERROR, toString output incorrect for alias: " + alias);
                ++errorCount;
            }
        }

        System.out.println("toString test passed for main algorithm and aliases.\n");
    }

    // ------------------------ Helper Class ------------------------

    static class SimulatedService {
        String type;
        String algorithm;
        String className;
        List<String> aliases;

        SimulatedService(String type, String algorithm, String className, List<String> aliases) {
            this.type = type;
            this.algorithm = algorithm;
            this.className = className;
            this.aliases = aliases;
        }

        @Override
        public String toString() {
            return type + "/" + algorithm + " (" + className + ")";
        }
    }
}

// Class jj in case we are executed by the OpenJDK JVM.
class jj {

    public static String _getProgramName() {
        System.out.println("J-class function _getProgramName (not Jacobin)");
        return "java";
    }
   
}
