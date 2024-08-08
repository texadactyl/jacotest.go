
public class main {

    final static int MAX_LOOPS = 100000;
    
    public static void runBuilder() {

        StringBuilder sb1 = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        char charray [] = { 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

        sb1.append(charray, 5, 3);
        sb1.append(charray, 0, 2);
        sb1.insert(2, charray, 2, 2);
        sb1.insert(0, charray, 2, 2);
        sb1 = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZxyz");        
        StringBuilder sb2 = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        sb2 = new StringBuilder("ABCDEFGHIJKLmNOPQRSTUVWXYZxyz");
        sb2 = sb1;
        sb1.setCharAt(2, 'c');
        sb1.setLength(5);
        sb1.setLength(7);
        String str = sb1.toString();
        
    }

    public static void runBuffer() {

        StringBuffer sb1 = new StringBuffer("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        char charray [] = { 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

        sb1.append(charray, 5, 3);
        sb1.append(charray, 0, 2);
        sb1.insert(2, charray, 2, 2);
        sb1.insert(0, charray, 2, 2);
        sb1 = new StringBuffer("ABCDEFGHIJKLMNOPQRSTUVWXYZxyz");        
        StringBuffer sb2 = new StringBuffer("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        sb2 = new StringBuffer("ABCDEFGHIJKLmNOPQRSTUVWXYZxyz");
        sb2 = sb1;
        sb1.setCharAt(2, 'c');
        sb1.setLength(5);
        sb1.setLength(7);
        String str = sb1.toString();
        
    }

    public static void main(String[] args) {
        
    	System.out.print("Loop count: ");
    	System.out.println(MAX_LOOPS);
 
        // StringBuilder
        System.out.println("runBuilder start .....");
    	long t1 = System.currentTimeMillis();
        for (int ndx = 0; ndx < MAX_LOOPS; ++ndx) {
            runBuilder();
        }
        long t2 = System.currentTimeMillis();
    	double secs_overall = (double) (t2 - t1) / 1000.0;
    	System.out.printf("runBuilder overall elapsed time = %.3f seconds\n", secs_overall); 

        // StringBuffer
        System.out.println("runBuffer start .....");
    	t1 = System.currentTimeMillis();
        for (int ndx = 0; ndx < MAX_LOOPS; ++ndx) {
            runBuffer();
        }
        t2 = System.currentTimeMillis();
    	secs_overall = (double) (t2 - t1) / 1000.0;
    	System.out.printf("runBuffer overall elapsed time = %.3f seconds\n", secs_overall); 
    }
}
