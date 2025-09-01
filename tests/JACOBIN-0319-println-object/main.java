public class main {

    // TODO: needs qualitative tests

    public static void printLabeledObject(String label, Object obj) {
        System.out.print(label);
        System.out.print(": ");
        System.out.println(obj);
    }

	public static void main(String[] args){
		Object obj;
		
		String ss = "42";
		obj = ss;
		printLabeledObject("String ss", obj);
		
		int ii = 42;
		obj = ii;
		printLabeledObject("int ii", obj);
		
		long jj = 42l;
		obj = jj;
		printLabeledObject("long jj", obj);
		
		float ff = 42f;
		obj = ff;
		printLabeledObject("float ff", obj);
		
		double dd = 42d;
		obj = dd;
		printLabeledObject("double dd", obj);
		
		byte bb = 0x42;
		obj = bb;
		printLabeledObject("byte bb", obj);
		
		obj = null;
		printLabeledObject("Object obj null", obj);

		obj = ss;
		printLabeledObject("Object obj (=ss)", obj);

		ss = null;
		printLabeledObject("String ss null", ss);

        ss = "";
		printLabeledObject("String ss \"\"", ss);
		
		Checkers.theEnd(0);
    }

}

