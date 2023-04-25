public class main {

    public static void main(String []args) throws Exception {

        String msg = "Try to get run.go to barf at line 1388";
        System.out.println(msg);

        int keySize = 256;
        int iterations = 65536;
        char[] password = "This is a huge secret!".toCharArray();
        System.out.println("password constructed");
        String originalString = "Mary had a little lamb.";
        System.out.println("originalString constructed");
        System.out.printf("Cleartext (string) [%d bytes]:\t%s\n", originalString.length(), originalString);
        byte[] msgBytes = originalString.getBytes();
        System.out.println("msgBytes constructed");
   
        System.out.println("No barfing");
    }

}
