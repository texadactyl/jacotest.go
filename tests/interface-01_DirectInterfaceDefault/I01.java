// Scenario 01: Direct interface default method
public interface I01 {
    default String greet() {
        return "Hello from I01 default";
    }
}
