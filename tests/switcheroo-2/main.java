
public final class main {

    private static int sw1(int arg) {
        switch(arg) {
            case 1, 2, 3:
                return 101;
            case 4, 5, 6:
                return 201;
            default:
                return 301;
        }
    }

    private static int sw2(int arg) {
        int value = switch(arg) {
            case 1, 2, 3:
                yield 101;
            case 4, 5, 6:
                yield 201;
            default:
                yield 301;
        };
        return value;
    }

    private static int sw3(int arg) {
        switch(arg) {
            case 1, 2, 3 -> { int ii = 101; return ii; }
            case 4, 5, 6 -> { int ii = 201; return ii; }
            default ->      { return 301; }
        }
    }

    public static void main(String[] args) {
        int errorCount = 0;
        Integer II = 1;
        Double Pi = 3.14159265;
        String SS = "abc";
        System.out.println("Enhanced switch statements");
        
        errorCount += Checkers.checker("sw1", 101, sw1(2));
        errorCount += Checkers.checker("sw1", 201, sw1(6));
        errorCount += Checkers.checker("sw1", 301, sw1(42));

        errorCount += Checkers.checker("sw2", 101, sw2(2));
        errorCount += Checkers.checker("sw2", 201, sw2(6));
        errorCount += Checkers.checker("sw2", 301, sw2(42));

        errorCount += Checkers.checker("sw3", 101, sw3(2));
        errorCount += Checkers.checker("sw3", 201, sw3(6));
        errorCount += Checkers.checker("sw3", 301, sw3(42));

        Checkers.theEnd(errorCount);
    }
    
}
