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

    public static void main(String[] args) {
    
       // LinkedList objects

        BigInteger BI = new BigInteger("42");
        System.out.println(BI);

		byte ba1[] = {1, 2, 3};
		byte ba2[] = {4, 5, 6};
		LinkedList<byte[]> baLL = new LinkedList<>();
		baLL.add(ba1);
		baLL.add(ba2);
		System.out.println(baLL);
		
		LinkedList<Byte> BYLL = new LinkedList<>();
		Byte BY = (byte)0xa1;	BYLL.add(BY);
		BY = (byte)0xa2;	BYLL.add(BY);
		BY = (byte)0xa3;	BYLL.add(BY);
		System.out.println(BYLL);

		LinkedList<Integer> IILL = new LinkedList<>();
		Integer II = 1;	IILL.add(II);
		II = 2;	IILL.add(II);
		II = 3;	IILL.add(II);
		System.out.println(IILL);

		LinkedList<Short> SHLL = new LinkedList<>();
		Short SH = 31;	SHLL.add(SH);
		SH = 32;	SHLL.add(SH);
		SH = 33;	SHLL.add(SH);
		System.out.println(SHLL);

		LinkedList<Long> JJLL = new LinkedList<>();
		Long JJ = 41L;	JJLL.add(JJ);
		JJ = 42L;	JJLL.add(JJ);
		JJ = 43L;	JJLL.add(JJ);
		System.out.println(JJLL);
		
		LinkedList<Double> DDLL = new LinkedList<>();
		Double DD = 1.1;	DDLL.add(DD);
		DD = 2.1;	DDLL.add(DD);
		DD = 3.1;	DDLL.add(DD);
		System.out.println(DDLL);
		
		LinkedList<Float> FFLL = new LinkedList<>();
		Float FF = 1.1f;	FFLL.add(FF);
		FF = 2.1f;	FFLL.add(FF);
		FF = 3.1f;	FFLL.add(FF);
		System.out.println(FFLL);

		LinkedList<String> strLL = new LinkedList<>();
		strLL.add("alpha");
		strLL.add("beta");
		strLL.add("gamma");
		System.out.println(strLL);
		
	    LinkedList<CustomObject> customLL = new LinkedList<>();
        customLL.add(new CustomObject(1, "Alice"));
        customLL.add(new CustomObject(2, "Bob"));
        customLL.add(new CustomObject(3, "Charlie"));
        System.out.println(customLL);
        
        // Non-LinkedList objects

	    CustomObject custom = new CustomObject(42, "Turtle");
        System.out.println(custom);
        
		System.out.println(ba1);
		System.out.println(ba2);
		
		System.out.println(BY);

		System.out.println(II);

		System.out.println(SH);

		System.out.println(JJ);
		
		System.out.println(DD);
		
		System.out.println(FF);

		String str = "gamma";
		System.out.println(str);
		
		System.out.println("I survived!");
		System.out.println("Console output should be eyeballed.");
		
    }
}
