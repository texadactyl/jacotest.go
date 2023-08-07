// SHA-3 calculator: https://emn178.github.io/online-tools/sha3_512.html

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

	public static int essai(int counter, byte[] messageBytes, String expectedHashHex) {
        Benchmark bmkNilMsg = new Benchmark(messageBytes);
        System.out.print(counter);
        System.out.print(". Expected: ");
        System.out.println(expectedHashHex);
        System.out.print(counter);
        System.out.print(". Observed: ");
        System.out.println(bmkNilMsg.hashHex);
        if (!bmkNilMsg.hashHex.equalsIgnoreCase(expectedHashHex)) {
            System.out.println("*** ERROR, Did not get the expected hash of a nil message!");
            return 1;
        }
        return 0;
	}

    public static void main(String args[]) {
        String msg = "SHA-3 hashing testa";
        System.out.println(msg);
        int errorCount = 0;

        byte[] messageBytes = "".getBytes();
        String expectedHashHex = "A69F73CCA23A9AC5C8B567DC185A756E97C982164FE25859E0D1DCC1475C80A615B2123AF1F5F94C11E3E9402C3AC558F500199D95B6D3E301758586281DCD26";
        errorCount += essai(1, messageBytes, expectedHashHex);

        messageBytes = "Mary had a little lamb".getBytes();
        expectedHashHex = "A19E4E4CAFF1ACB5E2FA0D97ED241FFA3132597F698320E6D9EC1FF13BF6A0DA711A4C0A0443749E1D310841A71F0C06988828B1FFCADF4A117FC366C350A272";
        errorCount += essai(2, messageBytes, expectedHashHex);

        messageBytes = new byte[4096];
        for (int ii = 0; ii < 4096; ++ii) {
        	messageBytes[ii] = (byte) (ii & 0xff);
        }
        expectedHashHex = "29D131EB6FAE7E2A457BA8E36852C3E763B282EDBD93E6767715B7F0FCA916E897CB691C2393708EFDE7F9E4F357C5DE291552C386D5848E63E39C8AC657C9CF";
        errorCount += essai(3, messageBytes, expectedHashHex);

        assert (errorCount == 0);
    }

}
