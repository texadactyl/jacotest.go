@FunctionalInterface interface TriIntFunction { int apply(int a,int b,int c); }
@FunctionalInterface interface TriStrFunction { String apply(String a,String b,String c); }
@FunctionalInterface interface QuadIntFunction { int apply(int a,int b,int c,int d); }
@FunctionalInterface interface QuadStrFunction { String apply(String a,String b,String c,String d); }
@FunctionalInterface interface PentaIntFunction { int apply(int a,int b,int c,int d,int e); }

public class main {

    /* ========================= Static methods ========================= */
    public static int sumThree(int x,int y,int z) { return x+y+z; }
    public static String joinThree(String a,String b,String c) { return a+"|"+b+"|"+c; }

    public static int sumFour(int a,int b,int c,int d) { return a+b+c+d; }
    public static String joinFour(String a,String b,String c,String d) { return a+"|"+b+"|"+c+"|"+d; }

    public static int sumFive(int a,int b,int c,int d,int e) { return a+b+c+d+e; }

    public static void main(String[] args) {
        int errorCount = 0;

        /* =================== Tri-argument method reference =================== */
        TriIntFunction f3 = main::sumThree;
        errorCount += Checkers.checker("TriIntFunction sumThree", 6, f3.apply(1,2,3));

        TriStrFunction s3 = main::joinThree;
        errorCount += Checkers.checker("TriStrFunction joinThree", "A|B|C", s3.apply("A","B","C"));

        /* =================== Quad-argument method reference =================== */
        QuadIntFunction f4 = main::sumFour;
        errorCount += Checkers.checker("QuadIntFunction sumFour", 10, f4.apply(1,2,3,4));

        QuadStrFunction s4 = main::joinFour;
        errorCount += Checkers.checker("QuadStrFunction joinFour", "A|B|C|D", s4.apply("A","B","C","D"));

        /* =================== Penta-argument method reference =================== */
        PentaIntFunction f5 = main::sumFive;
        errorCount += Checkers.checker("PentaIntFunction sumFive", 15, f5.apply(1,2,3,4,5));

        /* =================== High-arity lambdas =================== */
        TriIntFunction lambda3 = (x,y,z) -> x*y + z;
        errorCount += Checkers.checker("TriIntFunction lambda3", 11, lambda3.apply(3,2,5)); // 3*2 + 5 = 11

        QuadIntFunction lambda4 = (a,b,c,d) -> a+b*c-d;
        errorCount += Checkers.checker("QuadIntFunction lambda4", 7, lambda4.apply(1,3,2,0)); // 1+3*2-0=7

        PentaIntFunction lambda5 = (a,b,c,d,e) -> a+b-c+d*e;
        errorCount += Checkers.checker("PentaIntFunction lambda5", 16, lambda5.apply(1,5,2,3,4)); // 1+5-2+3*4=14

        /* =================== END =================== */
        Checkers.theEnd(errorCount);
    }
}

