public class main {

    public static void main(String args[]) {
        String msg = "Testing accessibility of String arrays, setting and getting values";
        System.out.println(msg);

        int[] arr_ints = new int[3];
        int size1 = arr_ints.length;
        System.out.print("arr_ints length: ");
        System.out.println(size1);
        arr_ints[0] = 0;
        arr_ints[1] = 1;
        arr_ints[2] = 2;
        System.out.println("Just set all of the int array elements");
        int ndx;
        String[] arr_strings = new String[3];
        int size2 = arr_strings.length;

        System.out.print("arr_strings length: ");
        System.out.println(size2);
        arr_strings[0] = "Mary had a little lamb";
        System.out.println("Just set String array element 0");
        arr_strings[1] = "whose fleece was white as snow";
        arr_strings[2] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()";
        System.out.println("Just set String array elements 1 and 2");
        for (ndx = 0; ndx < size2; ++ndx) {
            System.out.print(ndx);
            System.out.print(": ");
            System.out.println(arr_strings[ndx]);
        }
        System.out.println("End");
    }

}
