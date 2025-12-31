public class main {

    static int errorCount = 0;
    static byte expectedByte = 0x7F;
    static double expectedDouble = 1.2;
    static float expectedFloat = 3.4f;
    static int expectedInteger = 5;
    static long expectedLong = 6;
    static short expectedShort = 7;

    public static void main( String[] args) {
    
        System.out.println("Instanceof and cast to Number");
    
        Byte B = Byte.valueOf(expectedByte);
        Double D = Double.valueOf(expectedDouble);
        Float F = Float.valueOf(expectedFloat);
        Integer I = Integer.valueOf(expectedInteger);
        Long L = Long.valueOf(expectedLong);
        Short S = Short.valueOf(expectedShort);
        
        frank(B);
        frank(D);
        frank(F);
        frank(I);
        frank(L);
        frank(S);

        Checkers.theEnd(errorCount);
    }
  
    private static void frank(Object arg) {
        if (arg instanceof Number) {
            if (arg instanceof Byte) {
                byte B = ((Number) arg).byteValue();
                errorCount += Checkers.checker("frank Byte", expectedByte, B);
            } else if (arg instanceof Double) {
                double D = ((Number) arg).doubleValue();
                errorCount += Checkers.checker("frank Double", expectedDouble, D);
            } else if (arg instanceof Float) {
                float F = ((Number) arg).floatValue();
                errorCount += Checkers.checker("frank Float", expectedFloat, F);
            } else if (arg instanceof Integer) {
                int I = ((Number) arg).intValue();
                errorCount += Checkers.checker("frank Integer", expectedInteger, I);
            } else if (arg instanceof Long) {
                long L = ((Number) arg).longValue();
                errorCount += Checkers.checker("frank Long", expectedLong, L);
            } else if (arg instanceof Short) {
                short S = ((Number) arg).shortValue();
                errorCount += Checkers.checker("frank Short", expectedShort, S);
            } else {
                System.out.println("frank only does numerics");
                ++errorCount;
            }
        } else {
            System.out.println("frank only does instanceof Number");
            ++errorCount;
        }
    }

}

