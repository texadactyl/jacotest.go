
public class main {
    public static void main(String[] args) {
        int errorCount = 0; // initial error count

        // === NEWARRAY examples (primitive 1D arrays) ===
        System.out.println("=== NEWARRAY examples (primitive arrays) ===");

        int[] srcInt = {1, 2, 3, 4};
        int[] dstInt = new int[4];
        System.arraycopy(srcInt, 0, dstInt, 0, srcInt.length);
        printIntArray(dstInt);
        errorCount += Checkers.checkArraysEqual("int[]", srcInt, dstInt);

        short[] srcShort = {10, 20, 30};
        short[] dstShort = new short[3];
        System.arraycopy(srcShort, 0, dstShort, 0, srcShort.length);
        printShortArray(dstShort);
        errorCount += Checkers.checkArraysEqual("short[]", srcShort, dstShort);

        float[] srcFloat = {1.1f, 2.2f, 3.3f};
        float[] dstFloat = new float[3];
        System.arraycopy(srcFloat, 0, dstFloat, 0, srcFloat.length);
        printFloatArray(dstFloat);
        errorCount += Checkers.checkArraysEqual("float[]", srcFloat, dstFloat);

        double[] srcDouble = {1.11, 2.22, 3.33};
        double[] dstDouble = new double[3];
        System.arraycopy(srcDouble, 0, dstDouble, 0, srcDouble.length);
        printDoubleArray(dstDouble);
        errorCount += Checkers.checkArraysEqual("double[]", srcDouble, dstDouble);

        long[] srcLong = {100L, 200L, 300L};
        long[] dstLong = new long[3];
        System.arraycopy(srcLong, 0, dstLong, 0, srcLong.length);
        printLongArray(dstLong);
        errorCount += Checkers.checkArraysEqual("long[]", srcLong, dstLong);

        System.out.println();
        System.out.println("=== ANEWARRAY example (reference array) ===");
        String[] srcStr = {"alpha", "beta", "gamma"};
        String[] dstStr = new String[3];
        System.arraycopy(srcStr, 0, dstStr, 0, srcStr.length);
        printStringArray(dstStr);
        errorCount += Checkers.checkArraysEqual("String[]", srcStr, dstStr);

        // === MULTIANEWARRAY examples (2D primitive arrays) ===
        System.out.println();
        System.out.println("=== MULTIANEWARRAY examples (2D primitive arrays) ===");

        int[][] src2DInt = {{1, 2}, {3, 4}};
        int[][] dst2DInt = new int[2][2];
        for (int i = 0; i < src2DInt.length; i++) {
            System.arraycopy(src2DInt[i], 0, dst2DInt[i], 0, src2DInt[i].length);
        }
        print2DIntArray(dst2DInt);
        errorCount += Checkers.checkArraysEqual("int[][]", src2DInt, dst2DInt);

        short[][] src2DShort = {{10, 20}, {30, 40}};
        short[][] dst2DShort = new short[2][2];
        for (int i = 0; i < src2DShort.length; i++) {
            System.arraycopy(src2DShort[i], 0, dst2DShort[i], 0, src2DShort[i].length);
        }
        print2DShortArray(dst2DShort);
        errorCount += Checkers.checkArraysEqual("short[][]", src2DShort, dst2DShort);

        float[][] src2DFloat = {{1.1f, 2.2f}, {3.3f, 4.4f}};
        float[][] dst2DFloat = new float[2][2];
        for (int i = 0; i < src2DFloat.length; i++) {
            System.arraycopy(src2DFloat[i], 0, dst2DFloat[i], 0, src2DFloat[i].length);
        }
        print2DFloatArray(dst2DFloat);
        errorCount += Checkers.checkArraysEqual("float[][]", src2DFloat, dst2DFloat);

        double[][] src2DDouble = {{1.11, 2.22}, {3.33, 4.44}};
        double[][] dst2DDouble = new double[2][2];
        for (int i = 0; i < src2DDouble.length; i++) {
            System.arraycopy(src2DDouble[i], 0, dst2DDouble[i], 0, src2DDouble[i].length);
        }
        print2DDoubleArray(dst2DDouble);
        errorCount += Checkers.checkArraysEqual("double[][]", src2DDouble, dst2DDouble);

        long[][] src2DLong = {{100L, 200L}, {300L, 400L}};
        long[][] dst2DLong = new long[2][2];
        for (int i = 0; i < src2DLong.length; i++) {
            System.arraycopy(src2DLong[i], 0, dst2DLong[i], 0, src2DLong[i].length);
        }
        print2DLongArray(dst2DLong);
        errorCount += Checkers.checkArraysEqual("long[][]", src2DLong, dst2DLong);

        // Final summary
        Checkers.theEnd(errorCount);
    }

    // === 1D array print helpers ===
    private static void printIntArray(int[] a) {
        for (int i = 0; i < a.length; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(a[i]);
        }
        System.out.println();
    }

    private static void printShortArray(short[] a) {
        for (int i = 0; i < a.length; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(a[i]);
        }
        System.out.println();
    }

    private static void printFloatArray(float[] a) {
        for (int i = 0; i < a.length; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(a[i]);
        }
        System.out.println();
    }

    private static void printDoubleArray(double[] a) {
        for (int i = 0; i < a.length; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(a[i]);
        }
        System.out.println();
    }

    private static void printLongArray(long[] a) {
        for (int i = 0; i < a.length; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(a[i]);
        }
        System.out.println();
    }

    private static void printStringArray(String[] a) {
        for (int i = 0; i < a.length; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(a[i]);
        }
        System.out.println();
    }

    // === 2D array print helpers ===
    private static void print2DIntArray(int[][] arr) {
        for (int r = 0; r < arr.length; r++) {
            for (int c = 0; c < arr[r].length; c++) {
                if (c > 0) System.out.print(" ");
                System.out.print(arr[r][c]);
            }
            System.out.println();
        }
    }

    private static void print2DShortArray(short[][] arr) {
        for (int r = 0; r < arr.length; r++) {
            for (int c = 0; c < arr[r].length; c++) {
                if (c > 0) System.out.print(" ");
                System.out.print(arr[r][c]);
            }
            System.out.println();
        }
    }

    private static void print2DFloatArray(float[][] arr) {
        for (int r = 0; r < arr.length; r++) {
            for (int c = 0; c < arr[r].length; c++) {
                if (c > 0) System.out.print(" ");
                System.out.print(arr[r][c]);
            }
            System.out.println();
        }
    }

    private static void print2DDoubleArray(double[][] arr) {
        for (int r = 0; r < arr.length; r++) {
            for (int c = 0; c < arr[r].length; c++) {
                if (c > 0) System.out.print(" ");
                System.out.print(arr[r][c]);
            }
            System.out.println();
        }
    }

    private static void print2DLongArray(long[][] arr) {
        for (int r = 0; r < arr.length; r++) {
            for (int c = 0; c < arr[r].length; c++) {
                if (c > 0) System.out.print(" ");
                System.out.print(arr[r][c]);
            }
            System.out.println();
        }
    }
}

