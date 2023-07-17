public class main {

    public static void printLabeledObject(String label, Object obj) {
        System.out.print(label);
        System.out.print(": obj=");
        System.out.println(obj);
    }

	public static void main(String[] args){
		Object obj;
		
		String ss = "42";
		obj = ss;
		printLabeledObject("String ss: ", obj);
		
		Runtime RR = Runtime.getRuntime();
		obj = RR;
		printLabeledObject("Runtime RR: ", obj);
		
		int ii = 42;
		obj = ii;
		printLabeledObject("int ii: ", obj);
		
		long jj = 42l;
		obj = jj;
		printLabeledObject("long jj: ", obj);
		
		float ff = 42f;
		obj = ff;
		printLabeledObject("float ff: ", obj);
		
		double dd = 42f;
		obj = dd;
		printLabeledObject("double dd: ", obj);
		
		byte bb = 0x42;
		obj = bb;
		printLabeledObject("byte bb: ", obj);
    }

}

