/*
    See sub1.java instructions for making this program fail.
    With no changes, `java` and `jacobin` get the same results:
    
    1234567890123456
    31323334353637383930313233343536

*/

public class main {
    public static void main(String[] args) {
        String originalString = "1234567890123456";
        System.out.println(originalString);
        byte[] bb = originalString.getBytes();
        String hexString = sub1.toString(bb);
        System.out.println(hexString);
        assert hexString .equals("31323334353637383930313233343536");
        
        Checkers.theEnd(0);
    }
}

