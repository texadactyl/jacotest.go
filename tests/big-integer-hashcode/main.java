import java.math.BigInteger;

public class main {

    static int errorCount = 0;

    private static void hasher(long arg, int expected) {
        BigInteger bi = BigInteger.valueOf(arg);
        int observed = bi.hashCode();
        errorCount += Checkers.checker(String.format("hasher(%d)", arg), expected, observed);
    }

    private static void hasher(String arg, int expected) {
        BigInteger bi = new BigInteger(arg);
        int observed = bi.hashCode();
        errorCount += Checkers.checker(String.format("hasher(%s)", arg), expected, observed);
    }

    public static void main(String[] args) {
        
        hasher(0, 0);
        hasher(1, 1);
        hasher(100, 100);
        hasher(1000, 1000);
        hasher(32767, 32767);
        hasher(65535, 65535);
        hasher(1000000 - 1, 999999);
        hasher(1000000000 - 1, 999999999);
        hasher("93326215443944152681699238856266700490715968264381621468592963895217599993229915608941463976156518286253697920827223758251185210916864000000000000000000000000", -575588423);
        hasher("1000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000007", 838100529);

        hasher(-1, -1);
        hasher(-100, -100);
        hasher(-1000, -1000);
        hasher(-32767, -32767);
        hasher(-65535, -65535);
        hasher(-1000000 - 1, -1000001);
        hasher(-1000000000 - 1, -1000000001);
        hasher("-93326215443944152681699238856266700490715968264381621468592963895217599993229915608941463976156518286253697920827223758251185210916864000000000000000000000000", 575588423);
        hasher("-1000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000007", -838100529);
                
        Checkers.theEnd(errorCount);
   }
}

