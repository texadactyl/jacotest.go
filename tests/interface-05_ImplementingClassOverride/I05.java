// Scenario 05: Implementing class overrides a default method from the interface
public interface I05 {
    default String greet() {
        return "Hello from I05 default";
    }
}
