public class Primes {

    public static long[] findLargestTwoPrimes(long arg) {
        long first = -1, second = -1;
        
        for (long jj = arg; jj >= 2; jj--) {
            if (isPrime(jj)) {
                if (first == -1) {
                    first = jj;
                } else {
                    second = jj;
                    break;
                }
            }
        }
        return new long[]{first, second};
    }
    
    public static boolean isPrime(long arg) {
        if (arg < 2) return false;
        if (arg% 2 == 0) return arg == 2;
        for (long jj = 3; jj * jj <= arg; jj += 2) {
            if (arg % jj == 0) return false;
        }
        return true;
    }
}


