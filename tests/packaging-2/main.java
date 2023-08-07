import middle.pkgcalc.Calculator;

public class main {

    public static void main(String args[]) {
        System.out.println("Testing the use of import middle.pkgcalc.Calculator");
        Calculator obj = new Calculator();
        int result = obj.add(100, 200);
        assert (result == 300);
        System.out.println("Success with result == (100 + 200 = 300)");
    }

}

