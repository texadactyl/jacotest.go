@FunctionalInterface interface BoxMaker<T> {
    Box<T> make(T value);
}

@FunctionalInterface interface PairMaker<A,B> {
    Pair<A,B> make(A a, B b);
}

// Generic classes
class Box<T> {
    T value;
    Box(T v) { value = v; }
}

class Pair<A,B> {
    A first; B second;
    Pair(A a, B b) { first = a; second = b; }
}

public class main {

    public static void main(String[] args) {
        int errorCount = 0;

        /* ============================================================
           1. Single-parameter generic class constructor reference
           ============================================================ */
        BoxMaker<String> stringBoxMaker = Box<String>::new;
        Box<String> box = stringBoxMaker.make("Hello");
        errorCount += Checkers.checker("Box<String>::new", "Hello", box.value);

        BoxMaker<Integer> intBoxMaker = Box<Integer>::new;
        Box<Integer> intBox = intBoxMaker.make(42);
        errorCount += Checkers.checker("Box<Integer>::new", 42, (long)intBox.value);

        /* ============================================================
           2. Two-parameter generic class constructor reference
           ============================================================ */
        PairMaker<String,Integer> stringIntPairMaker = Pair<String,Integer>::new;
        Pair<String,Integer> pair = stringIntPairMaker.make("Age", 42);
        errorCount += Checkers.checker("Pair<String,Integer>::new", "Age=42",
                pair.first + "=" + pair.second);

        PairMaker<Integer,Integer> intPairMaker = Pair<Integer,Integer>::new;
        Pair<Integer,Integer> pair2 = intPairMaker.make(3, 5);
        errorCount += Checkers.checker("Pair<Integer,Integer>::new", "3=5",
                pair2.first + "=" + pair2.second);

        /* ============================================================
           END
           ============================================================ */
        Checkers.theEnd(errorCount);
    }
}

