// SHA-3 calculator: https://emn178.github.io/online-tools/sha3_512.html

import java.util.Properties;
import java.util.Random;

public class main {

    public static class Benchmark {
        byte[] hashBytes;
        double elapsedSeconds;
        String hashHex;
        int hashLength;

        public Benchmark(byte[] messageBytes) {
            long t1 = System.currentTimeMillis();
            hashBytes = FIPS202.HashFunction.SHA3_512.apply(messageBytes);
            long t2 = System.currentTimeMillis();
            elapsedSeconds = (double) (t2 - t1) / 1000.0;
            hashHex = FIPS202.hexFromBytes(hashBytes);
            hashLength = hashBytes.length;
        }
    }

    public static void main(String args[]) {
        String msg = "SHA-3 hashing tests";
        System.out.println(msg);

        int msgSize = 100000000;
        byte[] messageBytes;

        // look for runtime options
        if (args.length > 0) {
            if (args.length != 1 || args[0].equalsIgnoreCase("-h")) {
                System.out.println("\nUsage: [message size]\n");
                System.exit(1);
            }
            msgSize = Integer.parseInt(args[0]);
        }

        // hash a nil message and get expected result
        messageBytes = "".getBytes();
        Benchmark bmkNilMsg = new Benchmark(messageBytes);
        String expectedHashHex = "a69f73cca23a9ac5c8b567dc185a756e97c982164fe25859e0d1dcc1475c80a615b2123af1f5f94c11e3e9402c3ac558f500199d95b6d3e301758586281dcd26";
        if (!bmkNilMsg.hashHex.equalsIgnoreCase(expectedHashHex)) {
            System.out.println("*** Did not get the expected hash of a nil message!");
            System.out.println(String.format("*** Expected %d bytes: %s", expectedHashHex.length() / 2, expectedHashHex));
            System.out.println(String.format("*** Observed %d bytes: %s", bmkNilMsg.hashLength, bmkNilMsg.hashHex));
            System.exit(1);
        }

        // generate a non-nil message body
        Random rd = new Random();
        messageBytes = new byte[msgSize];
        rd.nextBytes(messageBytes);

        // run the benchmark
        Benchmark bmk = new Benchmark(messageBytes);

        // print results
        System.out.println(String.format("message size = %d bytes", messageBytes.length));
        System.out.println(String.format("elapsed time = %.2f seconds", bmk.elapsedSeconds));

    }

}
