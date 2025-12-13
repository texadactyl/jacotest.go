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
        System.out.printf("jvmPgmName=%s%n", jvmPgmName);

        TestServiceFieldsAndAliases();
        TestServiceToString();

        Checkers.theEnd(errorCount);
    }

    // ------------------------ Tests ------------------------

    static private void TestServiceFieldsAndAliases() {

        String[] aliases = { "RedApple", "GreenApple" };

        SimulatedService s =
            new SimulatedService("Fruit", "Apple", "GoRuntimeService", aliases);

        int entryCount = 1 + aliases.length;
        String[] names = new String[entryCount];
        SimulatedService[] services = new SimulatedService[entryCount];

        int idx = 0;
        names[idx] = s.algorithm;
        services[idx] = s;
        idx++;

        for (int i = 0; i < aliases.length; i++) {
            names[idx] = aliases[i];
            services[idx] = s;
            idx++;
        }

        System.out.println("\nService --> Alias mapping:");
        for (int i = 0; i < names.length; i++) {
            SimulatedService svc = services[i];
            System.out.printf(
                "Name: %s -> Type: %s, Algorithm: %s, ClassName: %s%n",
                names[i],
                svc.type,
                svc.algorithm,
                svc.className
            );
        }

        for (int i = 0; i < aliases.length; i++) {
            SimulatedService svc = lookup(names, services, aliases[i]);

            if (svc == null) {
                System.out.printf("*** ERROR, alias not mapped: %s%n", aliases[i]);
                ++errorCount;
            } else if (!"Apple".equals(svc.algorithm)
                    || !"Fruit".equals(svc.type)) {
                System.out.printf(
                    "*** ERROR, alias mapping incorrect for: %s%n",
                    aliases[i]
                );
                ++errorCount;
            }
        }
    }

    static private void TestServiceToString() {

        String[] aliases = { "RedApple", "GreenApple" };

        SimulatedService s =
            new SimulatedService("Fruit", "Apple", "GoRuntimeService", aliases);

        System.out.println("\nTesting toString() for main algorithm and aliases:");
        String expected = "Fruit/Apple (GoRuntimeService)";

        if (!expected.equals(s.toString())) {
            System.out.printf(
                "*** ERROR, main algorithm toString incorrect: %s%n",
                s.toString()
            );
            ++errorCount;
        }

        for (int i = 0; i < aliases.length; i++) {
            if (!expected.equals(s.toString())) {
                System.out.printf(
                    "*** ERROR, toString output incorrect for alias: %s%n",
                    aliases[i]
                );
                ++errorCount;
            }
        }

        System.out.println("toString test passed for main algorithm and aliases.\n");
    }

    // ------------------------ Lookup ------------------------

    static private SimulatedService lookup(
            String[] names,
            SimulatedService[] services,
            String key) {

        for (int i = 0; i < names.length; i++) {
            if (names[i].equals(key))
                return services[i];
        }
        return null;
    }

    // ------------------------ Helper Class ------------------------

    static class SimulatedService {
        String type;
        String algorithm;
        String className;
        String[] aliases;

        SimulatedService(
            String type,
            String algorithm,
            String className,
            String[] aliases
        ) {
            this.type = type;
            this.algorithm = algorithm;
            this.className = className;
            this.aliases = aliases;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(type);
            sb.append('/');
            sb.append(algorithm);
            sb.append(" (");
            sb.append(className);
            sb.append(')');
            return sb.toString();
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

