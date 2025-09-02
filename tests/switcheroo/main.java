public class main {

	private static int switcheroo(int step, int arg, boolean compact) {
		System.out.printf("step %d arg %d mode is ", step, arg);
		if (compact) {
			System.out.print("TABLESWITCH ");
			switch (arg) {
				case -1: System.out.println(", detected -1"); return arg;
				case 0: System.out.println(", detected 0"); return arg;
				case 1: System.out.println(", detected 1"); return arg;
				case 2: System.out.println(", detected 2"); return arg;
				case 3: System.out.println(", detected 3"); return arg;
				default: System.out.println(", defaulted"); return arg;
			}
		} else {
			System.out.print("LOOKUPSWITCH ");
			switch (arg) {
				case 1000: System.out.println(", detected 1000"); return arg;
				case 2000: System.out.println(", detected 2000"); return arg;
				case 3000: System.out.println(", detected 3000"); return arg;
				case -32767: System.out.println(", detected -32767"); return arg;
				default: System.out.println(", defaulted"); return arg;
			}
		}
	}
	
	public static void main(String[] args) {
		int sum = 0;
		sum += switcheroo(1, 3000, false);
		sum += switcheroo(2, 4000, false);
		sum += switcheroo(3, 1048576, false);
		sum += switcheroo(4, -4096, false);
		sum += switcheroo(5, 3, true);
		sum += switcheroo(6, 4, true);
		sum += switcheroo(7, 1048576, true);
		sum += switcheroo(8, -1, true);
		sum += switcheroo(9, 0, true);
		System.out.printf("Sum is %d\n", sum);
		
		int errorCount = Checkers.checker("sum == 2100062", 2100062, sum);
		Checkers.theEnd(errorCount);
	}
}

