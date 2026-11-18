import java.math.BigInteger;

class main {
  public static void main(String args[]) {
  
    final int ndigits = 100;
    final String expected[] = { "2718281828",
                            "4590452353",
                            "6028747135",
                            "2662497757",
                            "2470936999",
                            "5957496696",
                            "7627724076",
                            "6303535475",
                            "9457138217",
                            "8525166427" };
    int errorCount = 0;
   
    int k = binarySearch(ndigits);
    BigPair pair = sumTerms(0, k - 1);
    BigInteger p = pair.p;
    BigInteger q = pair.q;
    p = p.add(q);
    BigInteger a = BigInteger.valueOf(10).pow(ndigits - 1);
    BigInteger answer = p.multiply(a).divide(q);
    String answerStr = answer.toString();
    String observed;
    
    int row = 0;
    for (int i = 0; i < ndigits; i += 10) {
        char[] sb = new char[10];
        for (int j = i; j < i + 10; j++) {
            if (j < ndigits) {
              sb[j - i] = answerStr.charAt(j);
            } else {
              sb[j - i] = ' ';
            }
        }
        int count = i + 10;
        if (count > ndigits)
            count = ndigits;
        observed = new String(sb);
        errorCount += Checkers.checker(String.format("row %d", row), expected[row], observed);
        //System.out.printf("row %d: \t %s \t: %d\n", row, observed, count);
        row += 1;
    }
    
    Checkers.theEnd(errorCount);
    
  }

  static BigPair sumTerms(int a, int b) {
    if (b == a + 1) {
      BigPair pair = new BigPair(BigInteger.valueOf(1), BigInteger.valueOf(b));
      return pair;
    }
    int mid = (a + b) / 2;
    BigPair pairLeft = sumTerms(a, mid);
    BigPair pairRight = sumTerms(mid, b);
    return new BigPair(
        pairLeft.p.multiply(pairRight.q).add(pairRight.p), pairLeft.q.multiply(pairRight.q));
  }

  static int binarySearch(int n) {
    int a = 0;
    int b = 1;
    while (!testK(n, b)) {
      a = b;
      b *= 2;
    }
    while (b - a > 1) {
      int m = (a + b) / 2;
      if (testK(n, m)) {
        b = m;
      } else {
        a = m;
      }
    }
    return b;
  }

  static boolean testK(int n, int k) {
    if (k < 0) {
      return false;
    }
    double lnKFactorial = k * (Math.log((double) k) - 1) + 0.5 * Math.log(Math.PI * 2);
    double log10KFactorial = lnKFactorial / Math.log(10);
    return log10KFactorial >= (double) (n + 50);
  }
}

class BigPair {
  public BigInteger p, q;

  public BigPair(BigInteger p, BigInteger q) {
    this.p = p;
    this.q = q;
  }
}
