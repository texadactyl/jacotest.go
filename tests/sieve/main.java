// hacked from https://introcs.cs.princeton.edu/java/14array/PrimeSieve.java.html
/*
 *         upperBound         #Primes
 *  ---------------------------------
 *                 10               4
 *                100              25
 *              1,000             168
 *             10,000           1,229
 *            100,000           9,592
 *          1,000,000          78,498
 *         10,000,000         664,579
 *        100,000,000       5,761,455
 *      1,000,000,000      50,847,534
*/

import java.util.ArrayList;

class main {

	public static ArrayList<Integer> sieveOfEratosthenes(int upperBound) {
		// Create a boolean array "primeTest[0..n]" and
		// initialize all entries it as true. A value in
		// primeTest[i] will finally be false if i is Not a
		// primeTest, else true.
		boolean primeTest[] = new boolean[upperBound + 1];
		for (int ii = 0; ii <= upperBound; ii++)
			primeTest[ii] = true;

		for (int pp = 2; pp * pp <= upperBound; pp++) {
			// If primeTest[pp] is not changed, then it is a
			// primeTest
			if (primeTest[pp] == true) {
				// Update all multiples of p greater than or
				// equal to the square of it numbers which
				// are multiple of p and are less than p^2
				// are already been marked.
				for (int ii = pp * pp; ii <= upperBound; ii += pp)
					primeTest[ii] = false;
			}
		}

		// Collect all prime numbers and return array.
		ArrayList<Integer> primes = new ArrayList<>();
		for (int ii = 2; ii <= upperBound; ii++) {
			if (primeTest[ii] == true)
				primes.add(ii);
		}
		return primes;
	}

	public static void main(String args[]) {
		String nstr = "42";
		int upperBound;
		if (args.length == 1) {
			nstr = args[0]; // Use number from command line.
		}
		try {
			upperBound = Integer.parseInt(nstr);
		} catch (NumberFormatException ex) {
			throw new AssertionError("*** ERROR, Expected a numeric parameter!");
		}
		ArrayList<Integer> primes = sieveOfEratosthenes(upperBound);
		int numPrimes = primes.size();
		System.out.printf("Following are the %d prime numbers <= %d\n", numPrimes, upperBound);
		for (int ii = 0; ii < numPrimes; ++ii) {
			System.out.printf("%d\n", primes.get(ii));
		}
	}
}
