public class main {

	static int MAXLOOPS = 2000;
	static int MAXPRINT = 20;
	static int fails = 0;

	static long staticLong = 30;
	static long callCounter = 0;

	long fieldLong = 20;
	double fieldDouble = 4.5;
	int fieldInt = 7;

	static void check(String name, double got, double want) {
		if (got != want) {
			fails++;
			if (fails < MAXPRINT)
				System.out.println("*** ERROR, FAIL " + name + ": got " + got + ", want " + want);
			if (fails == (MAXPRINT+1))
				System.out.println("********** FAIL details curtailed after " + MAXPRINT + " entries");
		}
	}

	static long returnsLong() {
		callCounter++;
		return 123456789012L;
	}

	static double returnsDouble() {
		callCounter++;
		return 2.75;
	}

	public static void main(String[] args) {

		for (int loopIx = 0; loopIx < MAXLOOPS; loopIx++) {

			// dup2 on a long (javac: lload, dup2, lconst_1, ladd, lstore, lstore)
			long localLong = 10;
			long localLongCopy = localLong++;
			check("local long post-inc: copy", localLongCopy, 10);
			check("local long post-inc: var", localLong, 11);

			// dup2 on a long (javac: ldc2_w, dup2, lstore, lstore)
			long chainedFirst, chainedSecond;
			chainedFirst = chainedSecond = 5L;
			check("chained long assign: first", chainedFirst, 5);
			check("chained long assign: second", chainedSecond, 5);

			// dup2 on a double
			double localDouble = 1.5;
			double localDoubleCopy = localDouble++;
			check("local double post-inc: copy", localDoubleCopy, 1.5);
			check("local double post-inc: var", localDouble, 2.5);

			// static long: getstatic, dup2, lconst_1, ladd, putstatic
			staticLong = 30;
			long staticResult = staticLong++;
			check("static long post-inc: result", staticResult, 30);
			check("static long post-inc: staticLong", staticLong, 31);

			// long field: aload, dup, getfield, dup2_x1, lconst_1, ladd, putfield
			main fieldOwner = new main();
			long fieldLongResult = fieldOwner.fieldLong++;
			check("long field post-inc: result", fieldLongResult, 20);
			check("long field post-inc: field", fieldOwner.fieldLong, 21);

			// double field: dup2_x1 with a double
			double fieldDoubleResult = fieldOwner.fieldDouble++;
			check("double field post-inc: result", fieldDoubleResult, 4.5);
			check("double field post-inc: field", fieldOwner.fieldDouble, 5.5);

			// int field: dup_x1 (category-1 value over a category-1 reference)
			int fieldIntResult = fieldOwner.fieldInt++;
			check("int field post-inc: result", fieldIntResult, 7);
			check("int field post-inc: field", fieldOwner.fieldInt, 8);

			// long array element: aload, iload, dup2, laload, dup2_x2, lconst_1, ladd, lastore
			long[] longArray = {1, 2, 3};
			int element = 1;
			long longArrayResult = longArray[element]++;
			check("long array post-inc: result", longArrayResult, 2);
			check("long array post-inc: element", longArray[1], 3);
			check("long array post-inc: neighbor below", longArray[0], 1);
			check("long array post-inc: neighbor above", longArray[2], 3);

			// long array chained assign: aload, iload, ldc2_w, dup2_x2, lastore
			long longChained = longArray[element] = 99L;
			check("long array chained assign: result", longChained, 99);
			check("long array chained assign: element", longArray[1], 99);

			// double array element: dup2_x2 with a double
			double[] doubleArray = {1.5, 2.5, 3.5};
			double doubleArrayResult = doubleArray[element]++;
			check("double array post-inc: result", doubleArrayResult, 2.5);
			check("double array post-inc: element", doubleArray[1], 3.5);
			check("double array post-inc: neighbor below", doubleArray[0], 1.5);
			check("double array post-inc: neighbor above", doubleArray[2], 3.5);

			// int array compound assign: dup2 on two category-1 values (array ref + index)
			int[] intArray = {10, 20, 30};
			intArray[element] += 5;
			check("int array compound: element", intArray[1], 25);
			check("int array compound: neighbor below", intArray[0], 10);
			check("int array compound: neighbor above", intArray[2], 30);

			// int array post-inc, value used: dup_x2 (category-1 value over two category-1 values)
			int intArrayResult = intArray[element]++;
			check("int array post-inc: result", intArrayResult, 25);
			check("int array post-inc: element", intArray[1], 26);
			check("int array post-inc: neighbor below", intArray[0], 10);
			check("int array post-inc: neighbor above", intArray[2], 30);

			// discarded long and double call results: pop2 on a single category-2 value
			callCounter = 0;
			returnsLong();
			returnsDouble();
			check("discarded call results: call count", callCounter, 2);

		}

		if (fails > 0) {
			System.out.printf("*** ERROR, FAIL count: %d\n", fails);
			System.exit(1);
		}
		System.out.println("Success!");
	}
}
