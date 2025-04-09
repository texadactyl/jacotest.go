import java.util.LinkedList;
import java.math.BigInteger;

public class main {

    static class CustomObject {
        int id;
        String name;

        CustomObject(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "CustomObject{id=" + id + ", name='" + name + "'}";
        }
    }

    private static void printObject(String label, Object obj) {
        System.out.printf("printObject :: %s :: %s\n", label, obj.toString());
    }

    public static void main(String[] args) {
    
        String str;
        BigInteger BI = new BigInteger("42");
        System.out.println(BI);
        printObject("BigInteger BI", BI);
        System.out.println(); 

        byte[] ba1 = { (byte)0xa1, (byte)0xa2, (byte)0xa3};
        byte[] ba2 = { (byte)0xa4, (byte)0xa5, (byte)0xa6};
        System.out.println(ba1);
        System.out.println(ba2);
        printObject("byte[] ba1", ba1);
        printObject("byte[] ba2", ba2);
        System.out.println(); 
               
		LinkedList<byte[]> baLL = new LinkedList<>();
		baLL.add(ba1);
		baLL.add(ba2);
		System.out.println(baLL);
		printObject("LinkedList<byte[]> baLL", baLL);
        System.out.println(); 
		
		LinkedList<Byte> BYLL = new LinkedList<>();
		Byte BY = (byte)0xa1;	BYLL.add(BY);
		BY = (byte)0xa2;	    BYLL.add(BY);
		BY = (byte)0xa3;	    BYLL.add(BY);
		System.out.println(BYLL);
		printObject("LinkedList<Byte> BYLL", BYLL);
        System.out.println(); 

		LinkedList<Integer> IILL = new LinkedList<>();
		Integer II = 1;	    IILL.add(II);
		II = 2;	            IILL.add(II);
		II = 3;	            IILL.add(II);
		printObject("Integer II=3", II);
		System.out.println(IILL);
		printObject("LinkedList<Integer> IILL", IILL);
        System.out.println(); 

		LinkedList<Short> SHLL = new LinkedList<>();
		Short SH = 31;	SHLL.add(SH);
		SH = 32;	    SHLL.add(SH);
		SH = 33;	    SHLL.add(SH);
		printObject("Short SH=33", SH);
		System.out.println(SHLL);
		printObject("LinkedList<Short> SHLL", SHLL);
        System.out.println(); 

		LinkedList<Long> JJLL = new LinkedList<>();
		Long JJ = 41L;	JJLL.add(JJ);
		JJ = 42L;	    JJLL.add(JJ);
		JJ = 43L;	    JJLL.add(JJ);
		printObject("Long JJ=43", JJ);
		System.out.println(JJLL);
		printObject("LinkedList<Long> JJLL", JJLL);
        System.out.println(); 
		
		LinkedList<Double> DDLL = new LinkedList<>();
		Double DD = 1.1;	DDLL.add(DD);
		DD = 2.1;	        DDLL.add(DD);
		DD = 3.1;	        DDLL.add(DD);
		printObject("Double DD=3.1", DD);
		System.out.println(DDLL);
		printObject("LinkedList<Double> DDLL", DDLL);
        System.out.println(); 
		
		LinkedList<Float> FFLL = new LinkedList<>();
		Float FF = 101.1f;	FFLL.add(FF);
		FF = 102.1f;	        FFLL.add(FF);
		FF = 103.1f;	        FFLL.add(FF);
		printObject("Float FF=103.1", FF);
		System.out.println(FFLL);
		printObject("LinkedList<Float> FFLL", FFLL);
        System.out.println(); 

		LinkedList<String> strLL = new LinkedList<>();
		strLL.add("alpha");
		strLL.add("beta");
		strLL.add("gamma");
		System.out.println(strLL);
		printObject("LinkedList<String> strLL", strLL);
        System.out.println(); 
		
	    LinkedList<CustomObject> customLL = new LinkedList<>();
        customLL.add(new CustomObject(1, "Alice"));
        customLL.add(new CustomObject(2, "Bob"));
        customLL.add(new CustomObject(3, "Charlie"));
        System.out.println(customLL);
		printObject("LinkedList<CustomObject> customLL", customLL);        
	    CustomObject custom = new CustomObject(42, "Turtle");
        System.out.println(custom);
        printObject("CustomObject(42, \"Turtle\")", custom); 
        System.out.println(); 
       
		System.out.println("I survived!");

    }
}
