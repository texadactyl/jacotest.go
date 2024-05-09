import java.util.concurrent.atomic.AtomicInteger;

public class main {

    public static int checker(String label, int expected, int observed) {
        System.out.print("checker ");
        if (expected == observed) {
            System.out.print(label);
            System.out.printf(" ok, expected=%d, observed=%d\n", expected, observed);
            return 0;
        }
        System.out.print(label);
        System.out.printf(" *** ERROR, expected=%d, observed=%d\n", expected, observed);
        return 1;
    }

	private static int funk(int initialValue) {
		System.out.printf("\n===================== funk, initialValue=%d\n", initialValue);
		AtomicInteger ai = new AtomicInteger();
		int expected, observed, newValue;
		boolean boolie;
		int errorCount = 0;
		if (initialValue != 0) {
			ai = new AtomicInteger(initialValue);
			expected = initialValue;
		} else
			expected = 0;
		
		observed = ai.get();
		errorCount += checker("1 AtomicInteger <init>", expected, observed);
		
		expected = 100;
		ai.set(expected);
		observed = ai.get();
		errorCount += checker("2 AtomicInteger get", expected, observed);
		
		expected = 101;
		ai.lazySet(expected);
		observed = ai.get();
		errorCount += checker("3 AtomicInteger lazySet", expected, observed);
		
		expected = 101;
		newValue = 202;
		boolie = ai.compareAndSet(expected, newValue);
		errorCount += checker("4 AtomicInteger compareAndSet 1=true, 0=false", 1, true?1:0);
		
		expected = 202;
		observed = ai.getAndIncrement();
		errorCount += checker("5 AtomicInteger getAndIncrement", expected, observed);
		
		expected = 203;
		observed = ai.getAndDecrement();
		errorCount += checker("6 AtomicInteger getAndDecrement", expected, observed);
		
		expected = 202;
		observed = ai.getAndAdd(37);
		errorCount += checker("7 AtomicInteger getAndAdd", expected, observed);
		
		expected = 240;
		observed = ai.incrementAndGet();
		errorCount += checker("8 AtomicInteger incrementAndGet", expected, observed);
		
		expected = 239;
		observed = ai.decrementAndGet();
		errorCount += checker("9 AtomicInteger decrementAndGet", expected, observed);
		
		
		return errorCount;
	}

    public static void main(String[] args) {
    	int errorCount = 0;
    	errorCount += funk(42);
    	errorCount += funk(0);
    }
    
}
