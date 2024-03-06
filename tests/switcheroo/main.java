public class main {

	private static int switcheroo(int arg, boolean compact) {
		System.out.print("switcheroo: compact mode is ");
		System.out.println(compact);
		if (compact) {
			switch (arg) {
				case -1: System.out.println("switcheroo: detected -1"); return arg;
				case 0: System.out.println("switcheroo: detected 0"); return arg;
				case 1: System.out.println("switcheroo: detected 1"); return arg;
				case 2: System.out.println("switcheroo: detected 2"); return arg;
				case 3: System.out.println("switcheroo: detected 3"); return arg;
				default: System.out.printf("switcheroo: defaulted because of argument: %d\n", arg); return arg;
			}
		} else {
			switch (arg) {
				case 1000: System.out.println("switcheroo: detected 1000"); return arg;
				case 2000: System.out.println("switcheroo: detected 2000"); return arg;
				case 3000: System.out.println("switcheroo: detected 3000"); return arg;
				case -32767: System.out.println("switcheroo: detected -32767"); return arg;
				default: System.out.printf("switcheroo: defaulted because of argument: %d\n", arg); return arg;
			}
		}
	}
	
	public static void main(String[] args) {
		int sum = 0;
		sum += switcheroo(3000, false);
		sum += switcheroo(4000, false);
		sum += switcheroo(1048576, false);
		sum += switcheroo(-4096, false);
		sum += switcheroo(3, true);
		sum += switcheroo(4, true);
		sum += switcheroo(1048576, true);
		sum += switcheroo(-1, true);
		sum += switcheroo(0, true);
		System.out.printf("Sum is %d\n", sum);
		assert (sum == 2100062);
	}
}

