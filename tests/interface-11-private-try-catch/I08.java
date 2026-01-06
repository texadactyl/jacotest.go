interface I08 {
    // Private helper method introduced in Java 9 for default method composition
    private String helper(String name) {
        System.out.println("DEBUG entered helper");
        int one = 1;
        int zero = 0;
        try {
            int infinity = one / zero;;
            throw new AssertionError("*** ERROR, one / zero did not throw an exception!");
        } catch (Exception ex) {
            System.out.println("Caught divide by zero exception");
        }
        System.out.println("DEBUG leaving helper");
        return String.format("helper: %s", name);
    }

    // Default method can call private methods in the same interface
    default String greet(String name) {
        System.out.println("DEBUG entered greet");
        return String.format("greet: %s", helper(name));
    }

    // Static interface method – callable only via the interface name
    static String shout(String text) {
        return text.toUpperCase();
    }
}
