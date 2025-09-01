public class main {

    // TODO: add qualitative tests.

    final static int NLOOPS = 10000;

    private static void showResult(String elemType, int length, long t1, long t2) {
        double elapsedSeconds = (double) (t2 - t1) / 1000.0;
        System.out.print(elemType);
        System.out.print(" array size = ");
        System.out.print(length);
        System.out.print(", elapsed time = ");
        System.out.println(elapsedSeconds);
    }

    public static void main(String args[]) {
        int arraySize = NLOOPS;
        long t1, t2;

        String msg = "Testing accessibility of array elements of type boolean, byte, char, int, float, double, long, short, and String";
        System.out.println(msg);

        // byte array
        boolean[] bool_elems = new boolean[arraySize];
        t1 = System.currentTimeMillis();
        for (int ndx = 0; ndx < arraySize; ndx++) {
            if (ndx % 2 == 0)
                bool_elems[ndx] = true;
            else
                bool_elems[ndx] = false;
        }
        t2 = System.currentTimeMillis();
        bool_elems = null;
        showResult("boolean", arraySize, t1, t2);

        // byte array
        byte[] b_elems = new byte[arraySize];
        t1 = System.currentTimeMillis();
        for (int ndx = 0; ndx < arraySize; ndx++) {
            b_elems[ndx] = (byte) (ndx % 256);
        }
        t2 = System.currentTimeMillis();
        b_elems = null;
        showResult("byte", arraySize, t1, t2);

        // char array
        char[] c_elems = new char[arraySize];
        t1 = System.currentTimeMillis();
        for (int ndx = 0; ndx < arraySize; ndx++) {
            c_elems[ndx] = (char) (ndx % 256);
        }
        t2 = System.currentTimeMillis();
        c_elems = null;
        showResult("char", arraySize, t1, t2);

        // integer array
        int[] i_elems = new int[arraySize];
        t1 = System.currentTimeMillis();
        for (int ndx = 0; ndx < arraySize; ndx++) {
            i_elems[ndx] = (ndx % 256);
        }
        t2 = System.currentTimeMillis();
        i_elems = null;
        showResult("int", arraySize, t1, t2);

        // float array
        float[] f_elems = new float[arraySize];
        t1 = System.currentTimeMillis();
        for (int ndx = 0; ndx < arraySize; ndx++) {
            f_elems[ndx] = (float) (ndx % arraySize);
        }
        t2 = System.currentTimeMillis();
        f_elems = null;
        showResult("float", arraySize, t1, t2);

        // double array
        double[] d_elems = new double[arraySize];
        t1 = System.currentTimeMillis();
        for (int ndx = 0; ndx < arraySize; ndx++) {
            d_elems[ndx] = (double) (ndx % arraySize);
        }
        t2 = System.currentTimeMillis();
        d_elems = null;
        showResult("double", arraySize, t1, t2);

        // long array
        long[] j_elems = new long[arraySize];
        t1 = System.currentTimeMillis();
        for (int ndx = 0; ndx < arraySize; ndx++) {
            j_elems[ndx] = (ndx % 256);
        }
        t2 = System.currentTimeMillis();
        j_elems = null;
        showResult("long", arraySize, t1, t2);

        // short array
        short[] sh_elems = new short[arraySize];
        t1 = System.currentTimeMillis();
        for (int ndx = 0; ndx < arraySize; ndx++) {
            sh_elems[ndx] = (short) (ndx & 0x7fff);
        }
        t2 = System.currentTimeMillis();
        sh_elems = null;
        showResult("short", arraySize, t1, t2);

        // string array
        System.gc();
        String[] s_elems = new String[arraySize];
        t1 = System.currentTimeMillis();
        for (int ndx = 0; ndx < arraySize; ndx++) {
            s_elems[ndx] = String.valueOf(ndx % 256);
        }
        t2 = System.currentTimeMillis();
        s_elems = null;
        showResult("String", arraySize, t1, t2);

    }
}
