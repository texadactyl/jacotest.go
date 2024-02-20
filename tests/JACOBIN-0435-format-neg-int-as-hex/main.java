public class main {

    public static String printIntInHex(int arg) {
        byte[] bb = new byte[] {
                (byte)(arg >> 24),
                (byte)(arg >> 16),
                (byte)(arg >> 8),
                (byte)(arg),
        };
        String str = String.format("%02x%02x%02x%02x", bb[0], bb[1], bb[2], bb[3]);
        return str;
    }
    public static void main(String[] args) {
        int neg64 = -64;
        String expValue = new String("ffffffc0");
        String obsValue = printIntInHex(neg64);
        System.out.print("Expected value: ");
        System.out.println(expValue);
        System.out.print("Observed value: ");
        System.out.println(obsValue);
        assert (obsValue.equals(expValue));
    }
}  
