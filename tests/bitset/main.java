import java.util.BitSet;

public class main {

    public static void main(String[] args) {
        System.out.println("--- Starting BitSet Comprehensive Test ---");

        testConstructorsAndBasicProperties();
        testSetGetClearFlip();
        testBitwiseOperations();
        testSearchAndTraversal();
        testConversionAndSize();

        Checkers.theEnd(0);
    }

    private static void testConstructorsAndBasicProperties() {
        System.out.println("\n[Testing Constructors & Properties]");
        
        // Default constructor
        BitSet bs1 = new BitSet();
        // Sized constructor
        BitSet bs2 = new BitSet(128);

        assert bs1.isEmpty() : "New BitSet should be empty";
        assert bs1.length() == 0 : "New BitSet length should be 0";
        assert bs2.size() >= 128 : "BitSet size should be at least 128";

        // Equality and HashCode
        assert bs1.equals(new BitSet()) : "Empty BitSets should be equal";
        assert bs1.hashCode() == bs2.hashCode() : "Equal BitSets must have same hashCode";
    }

    private static void testSetGetClearFlip() {
        System.out.println("\n[Testing Set, Get, Clear, and Flip]");
        BitSet bs = new BitSet();

        // Individual bits
        bs.set(5);
        assert bs.get(5) == true;
        assert bs.get(4) == false;

        bs.set(5, false); // clear via set
        assert bs.get(5) == false;

        bs.flip(10);
        assert bs.get(10) == true;
        bs.flip(10);
        assert bs.get(10) == false;

        // Range operations
        bs.set(10, 15); // sets 10, 11, 12, 13, 14
        assert bs.get(10) && bs.get(14);
        assert !bs.get(15);

        bs.flip(12, 16); // 12, 13, 14 become false; 15 becomes true
        assert !bs.get(12) && !bs.get(14);
        assert bs.get(15);

        bs.clear(15);
        assert !bs.get(15);

        bs.clear(); // Clear all
        assert bs.isEmpty();
    }

    private static void testBitwiseOperations() {
        System.out.println("\n[Testing Bitwise Operations]");
        BitSet b1 = new BitSet();
        BitSet b2 = new BitSet();

        b1.set(1); b1.set(2); b1.set(3);
        b2.set(2); b2.set(3); b2.set(4);

        // Intersects
        assert b1.intersects(b2) : "b1 and b2 should intersect at 2 and 3";

        // Clone
        BitSet b1Copy = (BitSet) b1.clone();

        // AND
        b1.and(b2); // b1 is now {2, 3}
        assert b1.get(2) && b1.get(3) && !b1.get(1);

        // OR
        b1Copy.or(b2); // b1Copy is now {1, 2, 3, 4}
        assert b1Copy.get(1) && b1Copy.get(4);

        // XOR
        BitSet xorTarget = new BitSet();
        xorTarget.set(1); xorTarget.set(2);
        BitSet xorMask = new BitSet();
        xorMask.set(2); xorMask.set(3);
        xorTarget.xor(xorMask); // should be {1, 3}
        assert xorTarget.get(1) && xorTarget.get(3) && !xorTarget.get(2);

        // ANDNOT
        BitSet anTarget = new BitSet();
        anTarget.set(1); anTarget.set(2);
        BitSet anMask = new BitSet();
        anMask.set(2);
        anTarget.andNot(anMask); // should be {1}
        assert anTarget.get(1) && !anTarget.get(2);
    }

    private static void testSearchAndTraversal() {
        System.out.println("\n[Testing Search & Traversal]");
        BitSet bs = new BitSet();
        bs.set(10);
        bs.set(20);
        bs.set(30);

        // Next set bit
        assert bs.nextSetBit(0) == 10;
        assert bs.nextSetBit(10) == 10;
        assert bs.nextSetBit(11) == 20;
        assert bs.nextSetBit(31) == -1;

        // Next clear bit
        assert bs.nextClearBit(10) == 11;

        // Previous set bit
        assert bs.previousSetBit(25) == 20;
        assert bs.previousSetBit(9) == -1;

        // Previous clear bit
        assert bs.previousClearBit(10) == 9;
    }

    private static void testConversionAndSize() {
        System.out.println("\n[Testing Conversions & Sizing]");
        
        long[] initialArray = new long[] { 0b1011 }; // 1, 2, 8 set (value 11)
        BitSet bs = BitSet.valueOf(initialArray);

        assert bs.get(0);
        assert bs.get(1);
        assert !bs.get(2);
        assert bs.get(3);

        // Cardinality (number of true bits)
        assert bs.cardinality() == 3;

        // Exporting to arrays
        long[] longArray = bs.toLongArray();
        byte[] byteArray = bs.toByteArray();

        assert longArray.length > 0 && longArray[0] == 11;
        assert byteArray.length > 0 && byteArray[0] == 11;

        // toString implementation test (Using StringBuilder explicitly to prevent INVOKEDYNAMIC)
        StringBuilder sb = new StringBuilder();
        sb.append("String representation: ").append(bs.toString());
        System.out.println(sb.toString());
    }
}
