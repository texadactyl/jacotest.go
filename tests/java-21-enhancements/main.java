// Jacotest case exercising the headline Java 21 features described at:
// https://www.baeldung.com/java-lts-21-new-features
//
// Covered JEPs:
//   JEP 440 - Record Patterns
//   JEP 441 - Pattern Matching for switch
//   JEP 431 - Sequenced Collections
//   JEP 444 - Virtual Threads
//   JEP 452 - Key Encapsulation Mechanism API
//
// NOT covered: JEP 430 (String Templates). It was a preview feature in
// Java 21, requiring the compiler to be invoked with --enable-preview,
// and it was later reworked and ultimately withdrawn from later JDKs, so
// it is intentionally left out of this Jacotest case.

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.SequencedMap;
import java.util.SequencedSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.crypto.DecapsulateException;
import javax.crypto.KEM;
import javax.crypto.SecretKey;

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
            case Integer i when i < 0 -> "negative integer";
            case Integer i when i == 0 -> "zero";
            case Integer i -> "positive integer";
            case String s when s.isEmpty() -> "empty string";
            case String s -> "non-empty string: " + s;
            default -> "unrecognized: " + obj.getClass().getSimpleName();
        };
    }

    public static void main(String[] args) throws Exception {

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

        // -------------------------------------------------------------
        // JEP 431: Sequenced Collections
        // -------------------------------------------------------------

        List<String> names = new ArrayList<>(List.of("Alice", "Bob", "Charley"));
        names.addFirst("Zoe");
        names.addLast("Omega");
        System.out.printf("names: %s\n", names);
        errorCount += Checkers.checker("names.size() == 5", 5, names.size());
        errorCount += Checkers.checker("names.getFirst() == \"Zoe\"", "Zoe", names.getFirst());
        errorCount += Checkers.checker("names.getLast() == \"Omega\"", "Omega", names.getLast());

        List<String> reversedNames = names.reversed();
        System.out.printf("reversedNames: %s\n", reversedNames);
        errorCount += Checkers.checker("reversedNames.getFirst() == \"Omega\"", "Omega", reversedNames.getFirst());
        errorCount += Checkers.checker("reversedNames.getLast() == \"Zoe\"", "Zoe", reversedNames.getLast());

        names.removeFirst();
        names.removeLast();
        System.out.printf("names after removeFirst/removeLast: %s\n", names);
        errorCount += Checkers.checker("names.size() == 3 after removeFirst/removeLast", 3, names.size());
        errorCount += Checkers.checker("names.getFirst() == \"Alice\" after removeFirst", "Alice", names.getFirst());
        errorCount += Checkers.checker("names.getLast() == \"Charley\" after removeLast", "Charley", names.getLast());

        SequencedSet<Integer> numbers = new LinkedHashSet<>(List.of(10, 20, 30));
        System.out.printf("numbers.getFirst(): %d, numbers.getLast(): %d\n",
                numbers.getFirst(), numbers.getLast());
        errorCount += Checkers.checker("numbers.getFirst() == 10", 10, numbers.getFirst().intValue());
        errorCount += Checkers.checker("numbers.getLast() == 30", 30, numbers.getLast().intValue());

        SequencedSet<Integer> reversedNumbers = numbers.reversed();
        System.out.printf("reversedNumbers.getFirst(): %d\n", reversedNumbers.getFirst());
        errorCount += Checkers.checker("reversedNumbers.getFirst() == 30", 30,
                reversedNumbers.getFirst().intValue());

        SequencedMap<String, Integer> ranking = new LinkedHashMap<>();
        ranking.put("one", 1);
        ranking.put("two", 2);
        ranking.put("three", 3);
        System.out.printf("ranking.firstEntry(): %s, ranking.lastEntry(): %s\n",
                ranking.firstEntry(), ranking.lastEntry());
        errorCount += Checkers.checker("ranking.firstEntry().getKey() == \"one\"", "one",
                ranking.firstEntry().getKey());
        errorCount += Checkers.checker("ranking.lastEntry().getKey() == \"three\"", "three",
                ranking.lastEntry().getKey());

        SequencedMap<String, Integer> reversedRanking = ranking.reversed();
        System.out.printf("reversedRanking.firstEntry(): %s\n", reversedRanking.firstEntry());
        errorCount += Checkers.checker("reversedRanking.firstEntry().getKey() == \"three\"", "three",
                reversedRanking.firstEntry().getKey());

        // -------------------------------------------------------------
        // JEP 444: Virtual Threads
        // -------------------------------------------------------------

        AtomicBoolean sawVirtualThread = new AtomicBoolean(false);
        AtomicInteger virtualThreadResult = new AtomicInteger(0);

        Thread vt = Thread.ofVirtual().start(() -> {
            sawVirtualThread.set(Thread.currentThread().isVirtual());
            virtualThreadResult.set(21 * 2);
        });
        vt.join();

        System.out.printf("sawVirtualThread: %b, virtualThreadResult: %d\n",
                sawVirtualThread.get(), virtualThreadResult.get());
        errorCount += Checkers.checker("Thread.ofVirtual() thread isVirtual() == true", true,
                sawVirtualThread.get());
        errorCount += Checkers.checker("virtual thread computed 21 * 2 == 42", 42, virtualThreadResult.get());

        AtomicInteger taskTotal = new AtomicInteger(0);
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<Integer>> futures = new ArrayList<>();
            for (int i = 1; i <= 10; ++i) {
                int taskValue = i;
                futures.add(executor.submit(() -> {
                    taskTotal.addAndGet(taskValue);
                    return taskValue;
                }));
            }
            int sumOfResults = 0;
            for (Future<Integer> future : futures) {
                sumOfResults += future.get();
            }
            System.out.printf("virtual thread task sum via futures: %d\n", sumOfResults);
            errorCount += Checkers.checker("sum of 1..10 via virtual thread futures == 55", 55, sumOfResults);
        }
        System.out.printf("virtual thread taskTotal: %d\n", taskTotal.get());
        errorCount += Checkers.checker("taskTotal after 10 virtual thread tasks == 55", 55, taskTotal.get());

        // -------------------------------------------------------------
        // JEP 452: Key Encapsulation Mechanism API
        // -------------------------------------------------------------
        // The javax.crypto.KEM class ships in Java 21's base class library,
        // but whether a given JDK build's default providers register a KEM
        // algorithm such as "DHKEM" depends on the vendor/version. Rather
        // than fail the whole Jacotest case on an environment that lacks
        // the algorithm, this section reports what it finds and only
        // counts it as an error if the API is present but produces
        // mismatched shared secrets.
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("X25519");
            KeyPair recipientKeyPair = keyPairGenerator.generateKeyPair();

            KEM kem = KEM.getInstance("DHKEM");

            KEM.Encapsulator encapsulator = kem.newEncapsulator(recipientKeyPair.getPublic());
            KEM.Encapsulated encapsulated = encapsulator.encapsulate();
            SecretKey senderSecret = encapsulated.key();

            KEM.Decapsulator decapsulator = kem.newDecapsulator(recipientKeyPair.getPrivate());
            SecretKey recipientSecret = decapsulator.decapsulate(encapsulated.encapsulation());

            boolean secretsMatch = senderSecret.equals(recipientSecret);
            System.out.printf("KEM \"DHKEM\" secrets match: %b\n", secretsMatch);
            errorCount += Checkers.checker("KEM sender/recipient shared secrets match", true, secretsMatch);
        } catch (NoSuchAlgorithmException e) {
            System.out.printf("KEM \"DHKEM\" not available on this JDK/provider set: %s\n", e.getMessage());
        } catch (InvalidKeyException | DecapsulateException e) {
            System.out.printf("KEM setup failed: %s\n", e.getMessage());
        }

        Checkers.theEnd(errorCount);
    }
}
