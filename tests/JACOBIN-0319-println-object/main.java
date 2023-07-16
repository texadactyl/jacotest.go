public class main {

    public static void printLabeledObject(String label, Object obj) {
        System.out.print(label);
        System.out.print(": obj=");
        System.out.println(obj);
    }

	public static void main(String[] args){
		int ipos = 42;
		Object obj = ipos;
		printLabeledObject("ipos: ", obj);
    }

}
