public class Speck {

    private static final int ROUNDS = 32;

    private static long ROR(long x, int r) {
        return (x >>> r) | (x << (64 - r));
    }

    private static long ROL(long x, int r) {
        return (x << r) | (x >>> (64 - r));
    }

    private static void R(long[] x, long[] y, long k) {
        x[0] = ROR(x[0], 8);
        x[0] += y[0];
        x[0] ^= k;
        y[0] = ROL(y[0], 3);
        y[0] ^= x[0];
    }

    public static void encrypt(long[] cipherText, long[] clearText, long[] K) {
        long y = clearText[0];
        long x = clearText[1];
        long b = K[0];
        long a = K[1];

        long[] xBox = new long[]{x};
        long[] yBox = new long[]{y};

        R(xBox, yBox, b);
        for (int i = 0; i < ROUNDS - 1; i++) {
            long[] aBox = new long[]{a};
            long[] bBox = new long[]{b};
            R(aBox, bBox, i);
            a = aBox[0];
            b = bBox[0];
            R(xBox, yBox, b);
        }

        cipherText[0] = yBox[0];
        cipherText[1] = xBox[0];
    }

    private static void R_inv(long[] x, long[] y, long k) {
        y[0] ^= x[0];
        y[0] = ROR(y[0], 3);
        x[0] ^= k;
        x[0] -= y[0];
        x[0] = ROL(x[0], 8);
    }

    public static void decrypt(long[] clearText, long[] cipherText, long[] K) {
        long y = cipherText[0];
        long x = cipherText[1];
        long a = K[1];
        long b = K[0];

        // Prepare subkeys
        long[] aBox = new long[]{a};
        long[] bBox = new long[]{b};
        long[] subkeys = new long[ROUNDS];
        subkeys[0] = b;

        for (int i = 0; i < ROUNDS - 1; i++) {
            R(aBox, bBox, i);
            subkeys[i + 1] = bBox[0];
        }

        long[] xBox = new long[]{x};
        long[] yBox = new long[]{y};

        for (int i = ROUNDS - 1; i >= 1; i--) {
            R_inv(xBox, yBox, subkeys[i]);
        }

        R_inv(xBox, yBox, subkeys[0]);

        clearText[0] = yBox[0];
        clearText[1] = xBox[0];
    }
}

