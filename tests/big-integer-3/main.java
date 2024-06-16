import java.math.BigInteger;

public class main {

    private static int checker(String label, int expected, int observed) {
        if (expected == observed) {
            System.out.printf(">>> Success, %s: expected(%d) = observed(%d)\n", label, expected, observed);
            return 0;
        }
        System.out.printf("*** ERROR, %s: expected(%d) != observed(%d)\n", label, expected, observed);
        return 1;

    }

    public static void main(String[] args) {
        int errorCount = 0;
        
        BigInteger xx = new BigInteger("65531");
        BigInteger mm = new BigInteger("17");

        BigInteger zz = xx.modInverse(mm);
        System.out.println("after modInverse: xx, mm, zz");
        System.out.print(xx);
        System.out.print(" ");
        System.out.print(mm);
        System.out.print(" ");
        System.out.println(zz);
        errorCount += checker("after modInverse", zz.intValue(), 4);
        
        mm = new BigInteger("2031");
        zz = xx.modInverse(mm);
        System.out.println("after modInverse: xx, mm, zz");
        System.out.print(xx);
        System.out.print(" ");
        System.out.print(mm);
        System.out.print(" ");
        System.out.println(zz);
        errorCount += checker("after modInverse", zz.intValue(), 260);
        
        xx = new BigInteger("-43");
        zz = xx.mod(mm);
        System.out.println("after mod: xx, mm, zz");
        System.out.print(xx);
        System.out.print(" ");
        System.out.print(mm);
        System.out.print(" ");
        System.out.println(zz);
        errorCount += checker("after mod", zz.intValue(), 1988);

        zz = xx.remainder(mm);
        System.out.println("after remainder: xx, mm, zz");
        System.out.print(xx);
        System.out.print(" ");
        System.out.print(mm);
        System.out.print(" ");
        System.out.println(zz);
        errorCount += checker("after remainder", zz.intValue(), -43);

        xx = new BigInteger("1048576");
        BigInteger aa[] = xx.divideAndRemainder(mm);
        System.out.println("after divideAndRemainder: xx, mm, aa[0], aa[1]");
        System.out.print(xx);
        System.out.print(" ");
        System.out.print(mm);
        System.out.print(" ");
        System.out.print(aa[0]);
        System.out.print(" ");
        System.out.println(aa[1]);
        errorCount += checker("quotient after divideAndRemainder", aa[0].intValue(), 516);
        errorCount += checker("remainder after divideAndRemainder", aa[1].intValue(), 580);
        
        assert(errorCount == 0);
   }
}

