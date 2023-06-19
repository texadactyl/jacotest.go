public class main {

    public static int displayResult(String label, int size1, int size2) {
        if (size1 == size2) {
            System.out.print(label);
            System.out.println(" array length comapared ok");
            return 0;
        }
        System.out.print(label);
        System.out.print(" *FAILED* array length comparison ");
        System.out.print(size1);
        System.out.print(" : ");
        System.out.println(size2);
        return 1;
    }

    public static void main(String args[]) {
        int errorCount = 0;
        String msg = "Exercise array lengths for type byte, char, int, float, double, and String";
        System.out.println(msg);

        byte[] arr_byte = new byte[8192];
        errorCount += displayResult("byte", arr_byte.length, 8192);

        char[] arr_char = new char[8192];
        errorCount += displayResult("char", arr_char.length, 8192);

        int[] arr_int = new int[8192];
        errorCount += displayResult("int", arr_int.length, 8192);

        float[] arr_float = new float[8192];
        errorCount += displayResult("float", arr_float.length, 8192);

        double[] arr_double = new double[8192];
        errorCount += displayResult("double", arr_double.length, 8192);

        String[] arr_String = new String[8192];
        errorCount += displayResult("String", arr_String.length, 8192);

        if (errorCount == 0) {
            System.out.println("No errors detected");
        } else {
            System.out.print("Number of errors = ");
            System.out.println(errorCount);
            System.exit(1);
        }
    }
}

