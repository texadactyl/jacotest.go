// Jacotest case focused on java/lang/Record overrides.
//
// Each record explicitly declares toString(), equals() and hashCode(), so
// javac does not generate them via ObjectMethods.bootstrap (invokedynamic).
// This case checks that those overrides are the ones that run, and that the
// records still behave as java.lang.Record subclasses.
//
// Also covered: JEP 440 record patterns (instanceof, including nested).
// Deliberately avoided: lambdas, pattern switches, and '+' string
// concatenation (all of which compile to invokedynamic).

public class main {

    record Point(int x, int y) {
        @Override
        public boolean equals(Object o) {
            return o instanceof Point p && x == p.x && y == p.y;
        }

        @Override
        public int hashCode() {
            return 31 * x + y;
        }

        @Override
        public String toString() {
            return new StringBuilder("Point[x=").append(x).append(", y=").append(y).append("]").toString();
        }
    }

    record ColoredPoint(Point point, String color) {
        @Override
        public boolean equals(Object o) {
            return o instanceof ColoredPoint c && point.equals(c.point) && color.equals(c.color);
        }

        @Override
        public int hashCode() {
            return 31 * point.hashCode() + color.hashCode();
        }

        @Override
        public String toString() {
            return new StringBuilder("ColoredPoint[point=").append(point)
                    .append(", color=").append(color).append("]").toString();
        }
    }

    sealed interface Shape permits Circle, Rectangle, Triangle {}

    record Circle(double radius) implements Shape {
        @Override
        public boolean equals(Object o) {
            return o instanceof Circle c && Double.compare(radius, c.radius) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(radius);
        }

        @Override
        public String toString() {
            return String.format("Circle[radius=%.1f]", radius);
        }
    }

    record Rectangle(double width, double height) implements Shape {
        @Override
        public boolean equals(Object o) {
            return o instanceof Rectangle r
                    && Double.compare(width, r.width) == 0
                    && Double.compare(height, r.height) == 0;
        }

        @Override
        public int hashCode() {
            return 31 * Double.hashCode(width) + Double.hashCode(height);
        }

        @Override
        public String toString() {
            return String.format("Rectangle[width=%.1f, height=%.1f]", width, height);
        }
    }

    record Triangle(double base, double height) implements Shape {
        @Override
        public boolean equals(Object o) {
            return o instanceof Triangle t
                    && Double.compare(base, t.base) == 0
                    && Double.compare(height, t.height) == 0;
        }

        @Override
        public int hashCode() {
            return 31 * Double.hashCode(base) + Double.hashCode(height);
        }

        @Override
        public String toString() {
            return String.format("Triangle[base=%.1f, height=%.1f]", base, height);
        }
    }

    static String b(boolean v) {
        return String.valueOf(v);
    }

    public static void main(String[] args) {

        System.out.println("java/lang/Record overrides - Jacotest case");

        int errorCount = 0;

        Point p1 = new Point(3, 4);
        Point p2 = new Point(3, 4);
        Point p3 = new Point(4, 3);
        ColoredPoint cp = new ColoredPoint(new Point(5, 6), "RED");
        ColoredPoint cpSame = new ColoredPoint(new Point(5, 6), "RED");
        ColoredPoint cpOther = new ColoredPoint(new Point(5, 6), "BLUE");
        Shape circle = new Circle(2.0);
        Shape rectangle = new Rectangle(3.0, 4.0);
        Shape triangle = new Triangle(6.0, 5.0);

        // -------------------------------------------------------------
        // toString() overrides
        // -------------------------------------------------------------

        System.out.printf("Point toString: %s\n", p1.toString());
        errorCount += Checkers.checker("Point.toString()", "Point[x=3, y=4]", p1.toString());
        errorCount += Checkers.checker("ColoredPoint.toString() (nested)", "ColoredPoint[point=Point[x=5, y=6], color=RED]", cp.toString());
        errorCount += Checkers.checker("Circle.toString()", "Circle[radius=2.0]", circle.toString());
        errorCount += Checkers.checker("Rectangle.toString()", "Rectangle[width=3.0, height=4.0]", rectangle.toString());
        errorCount += Checkers.checker("Triangle.toString()", "Triangle[base=6.0, height=5.0]", triangle.toString());

        // -------------------------------------------------------------
        // equals() overrides
        // -------------------------------------------------------------

        Object notAPoint = "Point[x=3, y=4]";
        Object nullObj = null;

        errorCount += Checkers.checker("Point equal instances", "true", b(p1.equals(p2)));
        errorCount += Checkers.checker("Point unequal instances", "false", b(p1.equals(p3)));
        errorCount += Checkers.checker("Point.equals(null)", "false", b(p1.equals(nullObj)));
        errorCount += Checkers.checker("Point.equals(wrong type)", "false", b(p1.equals(notAPoint)));
        errorCount += Checkers.checker("ColoredPoint equal instances", "true", b(cp.equals(cpSame)));
        errorCount += Checkers.checker("ColoredPoint differing color", "false", b(cp.equals(cpOther)));
        errorCount += Checkers.checker("Circle equal instances", "true", b(circle.equals(new Circle(2.0))));
        errorCount += Checkers.checker("Circle vs Rectangle", "false", b(circle.equals(rectangle)));
        errorCount += Checkers.checker("Rectangle equal instances", "true",
                b(rectangle.equals(new Rectangle(3.0, 4.0))));
        errorCount += Checkers.checker("Triangle unequal instances", "false",
                b(triangle.equals(new Triangle(6.0, 6.0))));

        // -------------------------------------------------------------
        // hashCode() overrides
        // -------------------------------------------------------------

        errorCount += Checkers.checker("Point.hashCode() == 31*3+4", 97, p1.hashCode());
        errorCount += Checkers.checker("equal Points share hashCode", p1.hashCode(), p2.hashCode());
        errorCount += Checkers.checker("equal ColoredPoints share hashCode", cp.hashCode(), cpSame.hashCode());
        errorCount += Checkers.checker("Circle.hashCode() == Double.hashCode(2.0)",
                Double.hashCode(2.0), circle.hashCode());
        errorCount += Checkers.checker("Rectangle.hashCode()",
                31 * Double.hashCode(3.0) + Double.hashCode(4.0), rectangle.hashCode());

        // -------------------------------------------------------------
        // java.lang.Record identity
        // -------------------------------------------------------------

        Object pointObj = p1;
        Object cpObj = cp;
        Object stringObj = "not a record";

        errorCount += Checkers.checker("Point instanceof Record", "true", b(pointObj instanceof Record));
        errorCount += Checkers.checker("ColoredPoint instanceof Record", "true", b(cpObj instanceof Record));
        errorCount += Checkers.checker("Circle instanceof Record", "true", b(circle instanceof Record));
        errorCount += Checkers.checker("String instanceof Record", "false", b(stringObj instanceof Record));
        errorCount += Checkers.checker("Point.class.isRecord()", "true", b(pointObj.getClass().isRecord()));
        errorCount += Checkers.checker("Triangle.class.isRecord()", "true", b(triangle.getClass().isRecord()));
        errorCount += Checkers.checker("String.class.isRecord()", "false", b(stringObj.getClass().isRecord()));

        // -------------------------------------------------------------
        // JEP 440: record patterns
        // -------------------------------------------------------------

        int sum = 0;
        if (pointObj instanceof Point(int px, int py)) {
            sum = px + py;
        }
        errorCount += Checkers.checker("Point(x,y) record pattern sum == 7", 7, sum);

        int nestedSum = 0;
        String nestedColor = "";
        if (cpObj instanceof ColoredPoint(Point(int cx, int cy), String color)) {
            nestedSum = cx + cy;
            nestedColor = color;
        }
        errorCount += Checkers.checker("nested ColoredPoint record pattern sum == 11", 11, nestedSum);
        errorCount += Checkers.checker("nested ColoredPoint record pattern color == RED", "RED", nestedColor);

        Checkers.theEnd(errorCount);
    }
}
