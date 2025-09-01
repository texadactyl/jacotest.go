public class main {

    public static void main(String[] args) throws Exception {
        int i65537 = 65537;

        byte bb = (byte) i65537;
		int ii = (int) bb;
		System.out.printf("i65537=%d, byte: 0x%02x\n", i65537, ii);

        char cc = (char) i65537;
		ii = (int) cc;
		System.out.printf("i65537=%d, char: 0x%02x\n", i65537, ii);

        short sh = (short) i65537;
		System.out.printf("i65537=%d, short: %d\n", i65537, sh);

		assert (bb == '\u0001');
		assert (cc == '\u0001');
        assert (sh == 1);
        
        Checkers.theEnd(0);
    }
}
