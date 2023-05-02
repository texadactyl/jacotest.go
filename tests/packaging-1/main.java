// Hacked from https://beginnersbook.com/2013/03/packages-in-java/

import pkgcalc.Calculator;

public class main {

    public static void main(String args[]) {
        System.out.println("Testing the use of import pkgcalc.Calculator");
        Calculator obj = new Calculator();
        int result = obj.add(100, 200);
        if (result != 300) {
            System.out.println("*** FAILED *** pkgcalc.Calculator did not get a result of 300");
            System.out.print("************** observed a result of ");
            System.out.println(result);
            System.exit(1);
        }
        System.out.println("Success with result == (100 + 200 = 300)");
    }

}

