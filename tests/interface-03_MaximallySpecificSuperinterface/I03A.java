// Scenario 03: Maximally-specific superinterface default method
public interface I03A {
    default String who() {
        return "I03A.default who()";
    }
}
