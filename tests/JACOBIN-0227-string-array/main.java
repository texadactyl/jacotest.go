public class main {

    public static void main(String args[]) {
        int errorCount = 0;
        int ndx;
        
        System.out.println("Testing accessibility of String arrays, setting and getting values");

        int[] arr_ints = new int[3];
        int size1 = arr_ints.length;
        arr_ints[0] = 0;
        arr_ints[1] = 1;
        arr_ints[2] = 2;
        String[] arr_strings = new String[3];
        
        int size2 = arr_strings.length;        
        errorCount += Checkers.checker("arr_strings length", 3, size2);
        
        arr_strings[0] = "Mary had a little lamb";
        arr_strings[1] = "whose fleece was white as snow";
        arr_strings[2] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()";
        
        for (ndx = 0; ndx < size2; ++ndx) {
            switch(ndx) {
                case 0:
                    errorCount += Checkers.checker("index 0", "Mary had a little lamb", arr_strings[ndx]);
                    break;
                case 1:
                    errorCount += Checkers.checker("index 0", "whose fleece was white as snow", arr_strings[ndx]);
                    break;
                case 2:
                    errorCount += Checkers.checker("index 0", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()", arr_strings[ndx]);
                    break;
                default:
                    System.out.println("*** ERROR, default case should never been taken");
                    errorCount += 1;
            }
        }
        Checkers.theEnd(errorCount);
    }

}
