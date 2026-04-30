/* The Computer Language Benchmarks Game
   https://salsa.debian.org/benchmarksgame-team/benchmarksgame/

   modified by Henco Appel
   removed parallelization by hanabi1224
*/

import java.security.MessageDigest;

public final class main {

    static final byte getByte(final double[] Crb, final double CibY, final int x) {
        int res = 0;
        for (int i = 0; i < 8; i += 2) {
            double Zr1 = Crb[x + i];
            double Zi1 = CibY;

            double Zr2 = Crb[x + i + 1];
            double Zi2 = CibY;

            int b = 0;
            int j = 49;
            do {
                double nZr1 = Zr1 * Zr1 - Zi1 * Zi1 + Crb[x + i];
                Zi1 = Zr1 * Zi1 + Zr1 * Zi1 + CibY;
                Zr1 = nZr1;

                double nZr2 = Zr2 * Zr2 - Zi2 * Zi2 + Crb[x + i + 1];
                Zi2 = Zr2 * Zi2 + Zr2 * Zi2 + CibY;
                Zr2 = nZr2;

                if (Zr1 * Zr1 + Zi1 * Zi1 > 4) {
                    b |= 2;
                    if (b == 3)
                        break;
                }
                if (Zr2 * Zr2 + Zi2 * Zi2 > 4) {
                    b |= 1;
                    if (b == 3)
                        break;
                }
            } while (--j > 0);
            res = (res << 2) + b;
        }
        return (byte) (res ^ -1);
    }

    public static void main(String[] args) throws Exception {
        int N = 100;
        if (args.length >= 1) {
            N = Integer.parseInt(args[0]);
        }
        N = (N + 7) / 8 * 8;
        double[] Crb = new double[N + 7];
        double invN = 2.0 / N;
        for (int i = 0; i < N; i++) {
            Crb[i] = i * invN - 1.5;
        }
        int lineLen = (N - 1) / 8 + 1;
        byte[] data = new byte[N * lineLen];
        for (int y = 0; y < N; y++) {
            double Ciby = y * invN - 1.0;
            int offset = y * lineLen;
            for (int x = 0; x < lineLen; x++)
                data[offset + x] = getByte(Crb, Ciby, x * 8);
        }
        System.out.printf("P4\n%d %d\n", N, N);
        MessageDigest hasher = MessageDigest.getInstance("md5");
        hasher.update(data);
        String hash = toHexString(hasher.digest());
        int errorCount = Checkers.checker("MD5 hash", hash, "a579bcd428e6bf2dd967f54518c26085");
        Checkers.theEnd(errorCount);
    }

    public static String toHexString(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
