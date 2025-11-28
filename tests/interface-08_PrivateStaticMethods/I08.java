interface I08 {
    // Private helper method introduced in Java 9 for default method composition
    private String helper(String name) {
        return "[helper:" + name + "]";
    }

    // Default method can call private methods in the same interface
    default String greet(String name) {
        return "Hello " + name + " " + helper(name);
    }

    // Static interface method – callable only via the interface name
    static String shout(String text) {
        return text.toUpperCase();
    }
}
