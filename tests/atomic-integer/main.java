import java.util.concurrent.atomic.AtomicInteger;

public class main {

	private static int funk(int initialValue) {
	    AtomicInteger ai;
		System.out.printf("\n===================== funk, initialValue=%d\n", initialValue);
		int expected, observed, newValue;
		boolean boolie;
		int errorCount = 0;
		if (initialValue != 0) {
			ai = new AtomicInteger(initialValue);
			expected = initialValue;
		} else {
			ai = new AtomicInteger();
			expected = 0;
		}
		
		observed = ai.get();
		errorCount += Checkers.checker("1 AtomicInteger get after <init>", expected, observed);
		
		expected = 100;
		ai.set(expected);
		observed = ai.get();
		errorCount += Checkers.checker("2 AtomicInteger get", expected, observed);
		
		expected = 101;
		ai.lazySet(expected);
		observed = ai.get();
		errorCount += Checkers.checker("3 AtomicInteger lazySet", expected, observed);
		
		expected = 101;
		newValue = 202;
		boolie = ai.compareAndSet(expected, newValue);
		errorCount += Checkers.checker("4 AtomicInteger compareAndSet 1=true, 0=false", 1, true?1:0);
		
		expected = 202;
		observed = ai.getAndIncrement();
		errorCount += Checkers.checker("5 AtomicInteger getAndIncrement", expected, observed);
		
		expected = 203;
		observed = ai.getAndDecrement();
		errorCount += Checkers.checker("6 AtomicInteger getAndDecrement", expected, observed);
		
		expected = 202;
		observed = ai.getAndAdd(37);
		errorCount += Checkers.checker("7 AtomicInteger getAndAdd", expected, observed);
		
		expected = 240;
		observed = ai.incrementAndGet();
		errorCount += Checkers.checker("8 AtomicInteger incrementAndGet", expected, observed);
		
		expected = 239;
		observed = ai.decrementAndGet();
		errorCount += Checkers.checker("9 AtomicInteger decrementAndGet", expected, observed);
		
		ai.set(300);
		expected = 306;
		observed = ai.addAndGet(6);
		errorCount += Checkers.checker("10 AtomicInteger addAndGet", expected, observed);

        float ff = ai.floatValue();
		errorCount += Checkers.checker("11 AtomicInteger floatValue", "306.0", String.format("%04.1f", ff));
        		
        double dd = ai.doubleValue();
		errorCount += Checkers.checker("12 AtomicInteger doubleValue", "306.0", String.format("%04.1f", dd));
		
        String ss = ai.toString();		
		errorCount += Checkers.checker("13 AtomicInteger toString", "306", ss);
		
		return errorCount;
	}

    public static void main(String[] args) {
    	int errorCount = 0;
    	errorCount += funk(42);
    	errorCount += funk(0);
    	
    	Checkers.theEnd(errorCount);
    }
    
}
