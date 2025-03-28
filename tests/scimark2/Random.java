

/*
   Random.java - This is a 100% fake random number generator.
   The values used are for the purpose of diagnosis
   and not intended to be real-life in any way.

   By "cooking the books", we can get the same results on every scimark2 run.
*/

public class Random {

    // Not a real random number generator seed.
    int seed = 0;

    // Cooked-up data values.
    int index = -1;
    double[] values = {0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0};

    // Object instantiation without a specified seed.
    public Random() {
        this.seed = 0;
    }

    // Object instantiation with a specified seed (not used).
    public Random(int seed) {
        this.seed = seed;
    }

    // Return the next double value.
    public synchronized double nextDouble() {
        this.index = (this.index + 1) % values.length;
        return values[this.index];
    }
}