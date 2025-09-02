import java.math.BigDecimal;
import java.math.BigInteger;

public class main {

    public static void main(String[] args) {
    
        int errorCount = 0;
    
        String mary = "Mary had a little lamb 1234567890!@#$%^&*()";
        String strExpected = "4d617279206861642061206c6974746c65206c616d62203132333435363738393021402324255e262a2829";
        String strObserved = HexDump.bytesToHex(mary.getBytes());
        errorCount += Checkers.checker("String mary", strExpected, strObserved);
        
        byte[] bb = mary.getBytes();
        System.out.print(HexDump.dumpBytes("mary", bb, bb.length, HexDump.COLUMN_SIZE));
        
        errorCount += Checkers.checker("Primes.isPrime(13)", Primes.isPrime(13), true);
        errorCount += Checkers.checker("Primes.isPrime(14) is false", Primes.isPrime(14), false);
        
        long[] myPrimes = Primes.findLargestTwoPrimes(1000000);
        errorCount += Checkers.checker("Primes.findLargestTwoPrimes(1000000) #1", 999983l, myPrimes[0]);
        errorCount += Checkers.checker("Primes.findLargestTwoPrimes(1000000) #2", 999979l, myPrimes[1]);
        
        Checkers.theEnd(errorCount);
    }
    
} 

