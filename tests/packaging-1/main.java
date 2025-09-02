// Hacked from https://beginnersbook.com/2013/03/packages-in-java/

import pkgcalc.Calculator;

public class main {

    public static void main(String args[]) {
        int errorCount = 0;
        System.out.println("Testing the use of import pkgcalc.Calculator");
        Calculator obj = new Calculator();
        int result = obj.add(100, 200);
        errorCount += Checkers.checker("result: 100 + 200 = 300", 300, result);
        
        Checkers.theEnd(errorCount);
    }

}

