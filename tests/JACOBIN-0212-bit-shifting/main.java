public class main {
    public static void main( String[] args) {
        int failures = 0;
        String wstr;
 
        int a = -100;
        int b = a >> 2;        
        System.out.print("-100 >> 2: "); System.out.println(b);
        if(b != -25) {
            System.out.print("*** Observed ");
            System.out.print(b);
            System.out.println("  but expected -25");
            failures += 1;
        }

        b = a << 3;
        System.out.print("-100 << 3: "); System.out.println(b);
        if(b != -800) {
            System.out.print("*** Observed ");
            System.out.print(b);
            System.out.println("  but expected -800");
            failures += 1;
        }

        a = 100;
        b = a >> 2;
        System.out.print("+100 >> 2: "); System.out.println(b);
        if(b != 25) {
            System.out.print("*** Observed ");
            System.out.print(b);
            System.out.println("  but expected 25");
            failures += 1;
        }

        b = a << 3;
        System.out.print("+100 << 3: "); System.out.println(b);
        if(b != 800) {
            System.out.print("*** Observed ");
            System.out.print(b);
            System.out.println("  but expected 800");
            failures += 1;
        }
        
        System.exit(failures);
    }
}
