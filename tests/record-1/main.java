// Jacotest case exercising the headline Java 21 features described at:
// https://www.baeldung.com/java-lts-21-new-features
//
// Covered JEPs:
//   JEP 440 - Record Patterns
//   JEP 441 - Pattern Matching for switch
//
// NOT covered: JEP 430 (String Templates). It was a preview feature in
// Java 21, requiring the compiler to be invoked with --enable-preview,
// and it was later reworked and ultimately withdrawn from later JDKs, so
// it is intentionally left out of this Jacotest case.

public class main {

    // ---- JEP 440 / JEP 441 support types ----------------------------------

    record Point(int x, int y) {}

    record ColoredPoint(Point point, String color) {}

    sealed interface Shape permits Circle, Rectangle, Triangle {}

    record Circle(double radius) implements Shape {}

    record Rectangle(double width, double height) implements Shape {}

    record Triangle(double base, double height) implements Shape {}

    // JEP 441: exhaustive switch over a sealed hierarchy, with record
    // patterns (JEP 440) deconstructing each case, needs no default arm.
    static double area(Shape shape) {
        return switch (shape) {
            case Circle(double r) -> Math.PI * r * r;
            case Rectangle(double w, double h) -> w * h;
            case Triangle(double b, double h) -> 0.5 * b * h;
        };
    }

    // JEP 441: pattern matching for switch with a null case label and
    // "when" guard clauses on top of type patterns.
    static String classify(Object obj) {
        return switch (obj) {
            case null -> "null value";
            case Integer iObj when iObj.intValue() < 0 -> "negative integer";
            case Integer iObj when iObj.intValue() == 0 -> "zero";
            case Integer iObj when iObj.intValue() > 0 -> "positive integer";
            case String str when str.isEmpty() -> "empty string";
            case String str when !str.isEmpty() -> "non-empty string: " + str;
            default -> "unrecognized: " + obj.getClass().getSimpleName();
        };
    }

    public static void main(String[] args) {

        System.out.println("Java 21 features - Jacotest case");

        int errorCount = 0;

        // -------------------------------------------------------------
        // JEP 440: Record Patterns
        // -------------------------------------------------------------

        Object pointObj = new Point(3, 4);
        int sum = 0;
        if (pointObj instanceof Point(int px, int py)) {
            sum = px + py;
        }
        System.out.printf("record pattern Point(x, y) sum: %d\n", sum);
        errorCount += Checkers.checker("Point(x,y) record pattern sum == 7", 7, sum);

        ColoredPoint cp = new ColoredPoint(new Point(5, 6), "RED");
        int nestedSum = 0;
        String nestedColor = "";
        if (cp instanceof ColoredPoint(Point(int cx, int cy), String color)) {
            nestedSum = cx + cy;
            nestedColor = color;
        }
        System.out.printf("nested record pattern sum: %d, color: %s\n", nestedSum, nestedColor);
        errorCount += Checkers.checker("nested ColoredPoint record pattern sum == 11", 11, nestedSum);
        errorCount += Checkers.checker("nested ColoredPoint record pattern color == RED", "RED", nestedColor);

        // -------------------------------------------------------------
        // JEP 441 + JEP 440: exhaustive switch over a sealed hierarchy
        // -------------------------------------------------------------

        String circleArea = String.format("%.4f", area(new Circle(2.0)));
        String expectedCircleArea = String.format("%.4f", Math.PI * 2.0 * 2.0);
        System.out.printf("circle area: %s\n", circleArea);
        errorCount += Checkers.checker("area(Circle(2.0)) == pi * 2 * 2", expectedCircleArea, circleArea);

        double rectangleArea = area(new Rectangle(3.0, 4.0));
        System.out.printf("rectangle area: %.4f\n", rectangleArea);
        errorCount += Checkers.checker("area(Rectangle(3.0, 4.0)) == 12.0", "12.0000",
                String.format("%.4f", rectangleArea));

        double triangleArea = area(new Triangle(6.0, 5.0));
        System.out.printf("triangle area: %.4f\n", triangleArea);
        errorCount += Checkers.checker("area(Triangle(6.0, 5.0)) == 15.0", "15.0000",
                String.format("%.4f", triangleArea));

        // -------------------------------------------------------------
        // JEP 441: null case label and "when" guards
        // -------------------------------------------------------------

        String classifyNull = classify(null);
        String classifyNeg = classify(-5);
        String classifyZero = classify(0);
        String classifyPos = classify(42);
        String classifyEmpty = classify("");
        String classifyStr = classify("Alice");

        System.out.printf("classify(null): %s\n", classifyNull);
        System.out.printf("classify(-5): %s\n", classifyNeg);
        System.out.printf("classify(0): %s\n", classifyZero);
        System.out.printf("classify(42): %s\n", classifyPos);
        System.out.printf("classify(\"\"): %s\n", classifyEmpty);
        System.out.printf("classify(\"Alice\"): %s\n", classifyStr);

        errorCount += Checkers.checker("classify(null) == \"null value\"", "null value", classifyNull);
        errorCount += Checkers.checker("classify(-5) == \"negative integer\"", "negative integer", classifyNeg);
        errorCount += Checkers.checker("classify(0) == \"zero\"", "zero", classifyZero);
        errorCount += Checkers.checker("classify(42) == \"positive integer\"", "positive integer", classifyPos);
        errorCount += Checkers.checker("classify(\"\") == \"empty string\"", "empty string", classifyEmpty);
        errorCount += Checkers.checker("classify(\"Alice\") == \"non-empty string: Alice\"",
                "non-empty string: Alice", classifyStr);

        Checkers.theEnd(errorCount);
    }
}
