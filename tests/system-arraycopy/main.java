import java.util.Arrays;

public class main {

    public static void main(String[] args) {
        int errorCount = 0; // initial error count

        // === NEWARRAY examples (primitive 1D arrays) ===
        System.out.println("=== NEWARRAY examples (primitive arrays) ===");

        int[] srcInt = {1, 2, 3, 4};
        int[] dstInt = new int[4];
        System.arraycopy(srcInt, 0, dstInt, 0, srcInt.length);
        printIntArray("srcInt", srcInt);
        printIntArray("dstInt", dstInt);
        errorCount += Checkers.checker("int[] srcInt == dstInt", true, Arrays.equals(srcInt, dstInt));

        double[] srcDouble = {1D, 2D, 3D, 4D};
        double[] dstDouble = new double[4];
        System.arraycopy(srcDouble, 0, dstDouble, 0, srcDouble.length);
        printDoubleArray("srcDouble", srcDouble);
        printDoubleArray("dstDouble", dstDouble);
        errorCount += Checkers.checker("int[] srcDouble == dstDouble", true, Arrays.equals(srcDouble, dstDouble));

        float[] srcFloat = {1F, 2F, 3F, 4F};
        float[] dstFloat = new float[4];
        System.arraycopy(srcFloat, 0, dstFloat, 0, srcFloat.length);
        printFloatArray("srcFloat", srcFloat);
        printFloatArray("dstFloat", dstFloat);
        errorCount += Checkers.checker("int[] srcFloat == dstFloat", true, Arrays.equals(srcFloat, dstFloat));

        long[] srcLong = {1L, 2L, 3L, 4L};
        long[] dstLong = new long[4];
        System.arraycopy(srcLong, 0, dstLong, 0, srcLong.length);
        printLongArray("srcLong", srcLong);
        printLongArray("dstLong", dstLong);
        errorCount += Checkers.checker("long[] srcLong == dstLong", true, Arrays.equals(srcLong, dstLong));

        short[] srcShort = {1, 2, 3, 4};
        short[] dstShort = new short[4];
        System.arraycopy(srcShort, 0, dstShort, 0, srcShort.length);
        printShortArray("srcShort", srcShort);
        printShortArray("dstShort", dstShort);
        errorCount += Checkers.checker("int[] srcShort == dstShort", true, Arrays.equals(srcShort, dstShort));

        System.out.println("\n=== ANEWARRAY examples ===");
        String srcStr[] = {"alpha", "beta", "gamma"};
        String dstStr[] = new String[3];
        System.arraycopy(srcStr, 0, dstStr, 0, srcStr.length);
        printStringArray("srcStr", srcStr);
        printStringArray("dstStr", dstStr);
        errorCount += Checkers.checker("String[] srcStr == dstStr", true, Arrays.equals(srcStr, dstStr));

        MyClassA objArray1[] = { new MyClassA(1), new MyClassA(2), new MyClassA(3)};
        MyClassA objArray2[] = { new MyClassA(111), new MyClassA(222), new MyClassA(333)};
        System.arraycopy(objArray1, 0, objArray2, 0, objArray1.length);
        errorCount += Checkers.checker("MyClassA[] objArray1 == objArray2", true, Arrays.equals(objArray1, objArray2));
        
        objArray2[0].xx = 42;
        errorCount += Checkers.checker("MyClassA[] objArray1 == objArray2 in spite of xx=42", true, Arrays.equals(objArray1, objArray2));

        MyClassB objArrayB[] = { new MyClassB(1), new MyClassB(2), new MyClassB(3)};
        errorCount += Checkers.checker("MyClassA[] objArray1 != MyClassB[] objArrayB", false, Arrays.equals(objArray1, objArrayB));

        System.out.println("\n=== MULTIANEWARRAY examples (2D primitive arrays) ===");

        int[][] src2DInt = {{1, 2}, {3, 4}};
        int[][] dst2DInt = new int[2][2];
        for (int i = 0; i < src2DInt.length; i++) {
            System.arraycopy(src2DInt[i], 0, dst2DInt[i], 0, src2DInt[i].length);
        }
        print2DIntArray("src2DInt", src2DInt);
        print2DIntArray("dst2DInt", dst2DInt);
        for (int i = 0; i < src2DInt.length; i++) {
            errorCount += Checkers.checker(String.format("int[][] src2DInt[%d] == dst2DInt[%d]", i, i), true, Arrays.equals(src2DInt[i], dst2DInt[i]));
        }

        double[][] src2DDouble = {{1D, 2D}, {3D, 4D}};
        double[][] dst2DDouble = new double[2][2];
        for (int i = 0; i < src2DDouble.length; i++) {
            System.arraycopy(src2DDouble[i], 0, dst2DDouble[i], 0, src2DDouble[i].length);
        }
        print2DDoubleArray("src2DDouble", src2DDouble);
        print2DDoubleArray("dst2DDouble", dst2DDouble);
        for (int i = 0; i < src2DDouble.length; i++) {
            errorCount += Checkers.checker(String.format("int[][] src2DDouble[%d] == dst2DDouble[%d]", i, i), true, Arrays.equals(src2DDouble[i], dst2DDouble[i]));
        }

        float[][] src2DFloat = {{1F, 2F}, {3F, 4F}};
        float[][] dst2DFloat = new float[2][2];
        for (int i = 0; i < src2DFloat.length; i++) {
            System.arraycopy(src2DFloat[i], 0, dst2DFloat[i], 0, src2DFloat[i].length);
        }
        print2DFloatArray("src2DFloat", src2DFloat);
        print2DFloatArray("dst2DFloat", dst2DFloat);
        for (int i = 0; i < src2DFloat.length; i++) {
            errorCount += Checkers.checker(String.format("int[][] src2DFloat[%d] == dst2DFloat[%d]", i, i), true, Arrays.equals(src2DFloat[i], dst2DFloat[i]));
        }

        long[][] src2DLong = {{1L, 2L}, {3L, 4L}};
        long[][] dst2DLong = new long[2][2];
        for (int i = 0; i < src2DLong.length; i++) {
            System.arraycopy(src2DLong[i], 0, dst2DLong[i], 0, src2DLong[i].length);
        }
        print2DLongArray("src2DLong", src2DLong);
        print2DLongArray("dst2DLong", dst2DLong);
        for (int i = 0; i < src2DLong.length; i++) {
            errorCount += Checkers.checker(String.format("int[][] src2DLong[%d] == dst2DLong[%d]", i, i), true, Arrays.equals(src2DLong[i], dst2DLong[i]));
        }

        short[][] src2DShort = {{1, 2}, {3, 4}};
        short[][] dst2DShort = new short[2][2];
        for (int i = 0; i < src2DShort.length; i++) {
            System.arraycopy(src2DShort[i], 0, dst2DShort[i], 0, src2DShort[i].length);
        }
        print2DShortArray("src2DShort", src2DShort);
        print2DShortArray("dst2DShort", dst2DShort);
        for (int i = 0; i < src2DShort.length; i++) {
            errorCount += Checkers.checker(String.format("int[][] src2DShort[%d] == dst2DShort[%d]", i, i), true, Arrays.equals(src2DShort[i], dst2DShort[i]));
        }

        // Final summary
        Checkers.theEnd(errorCount);
    }

    // === 1D array print helpers ===
    private static void printIntArray(String label, int[] a) {
        System.out.printf("%s :: ", label);
        for (int i = 0; i < a.length; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(a[i]);
        }
        System.out.println();
    }

    private static void printDoubleArray(String label, double[] a) {
        System.out.printf("%s :: ", label);
        for (int i = 0; i < a.length; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(a[i]);
        }
        System.out.println();
    }

    private static void printFloatArray(String label, float[] a) {
        System.out.printf("%s :: ", label);
        for (int i = 0; i < a.length; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(a[i]);
        }
        System.out.println();
    }

    private static void printLongArray(String label, long[] a) {
        System.out.printf("%s :: ", label);
        for (int i = 0; i < a.length; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(a[i]);
        }
        System.out.println();
    }

    private static void printShortArray(String label, short[] a) {
        System.out.printf("%s :: ", label);
        for (int i = 0; i < a.length; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(a[i]);
        }
        System.out.println();
    }

    private static void printStringArray(String label, String[] a) {
        System.out.printf("%s :: ", label);
        for (int i = 0; i < a.length; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(a[i]);
        }
        System.out.println();
    }

    // === 2D array print helpers ===
    private static void print2DIntArray(String label, int[][] arr) {
        System.out.printf("%s :: ", label);
        for (int r = 0; r < arr.length; r++) {
            for (int c = 0; c < arr[r].length; c++) {
                if (c > 0) System.out.print(" ");
                System.out.print(arr[r][c]);
            }
            System.out.println();
        }
    }

    private static void print2DDoubleArray(String label, double[][] arr) {
        System.out.printf("%s :: ", label);
        for (int r = 0; r < arr.length; r++) {
            for (int c = 0; c < arr[r].length; c++) {
                if (c > 0) System.out.print(" ");
                System.out.print(arr[r][c]);
            }
            System.out.println();
        }
    }

    private static void print2DFloatArray(String label, float[][] arr) {
        System.out.printf("%s :: ", label);
        for (int r = 0; r < arr.length; r++) {
            for (int c = 0; c < arr[r].length; c++) {
                if (c > 0) System.out.print(" ");
                System.out.print(arr[r][c]);
            }
            System.out.println();
        }
    }

    private static void print2DLongArray(String label, long[][] arr) {
        System.out.printf("%s :: ", label);
        for (int r = 0; r < arr.length; r++) {
            for (int c = 0; c < arr[r].length; c++) {
                if (c > 0) System.out.print(" ");
                System.out.print(arr[r][c]);
            }
            System.out.println();
        }
    }
    
    private static void print2DShortArray(String label, short[][] arr) {
        System.out.printf("%s :: ", label);
        for (int r = 0; r < arr.length; r++) {
            for (int c = 0; c < arr[r].length; c++) {
                if (c > 0) System.out.print(" ");
                System.out.print(arr[r][c]);
            }
            System.out.println();
        }
    }

}

class MyClassA {
    int xx = 0;
    MyClassA(int arg) {
        xx = arg;
    }
}

class MyClassB {
    int xx = 0;
    MyClassB(int arg) {
        xx = arg;
    }
}

