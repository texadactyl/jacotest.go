public class main {


    public static void main(String[] args) {
    
        int errorCount = 0;
        int cmpResult;
        boolean bbtrue = true;
        boolean bbfalse = false;
    	System.out.println("Boolean exercises");
    	
    	Boolean BBTrue1 = Boolean.valueOf("true");
    	Boolean BBTrue2 = Boolean.valueOf(true);
    	errorCount += Checkers.checker("BBTrue1 == BBTrue2?", BBTrue1.booleanValue(), BBTrue2.booleanValue());
    	
    	Boolean BBFalse1 = Boolean.valueOf("false");
    	Boolean BBFalse2 = Boolean.valueOf(false);
    	errorCount += Checkers.checker("BBFalse1 == BBFalse2?", BBFalse1.booleanValue(), BBFalse2.booleanValue());
    	
    	cmpResult = Boolean.compare(true, true);
    	errorCount += Checkers.checker("Boolean.compare(true, true)", 0, cmpResult);
    	cmpResult = Boolean.compare(false, true);
    	errorCount += Checkers.checker("Boolean.compare(false, true)", -1, cmpResult);
    	cmpResult = Boolean.compare(true, false);
    	errorCount += Checkers.checker("Boolean.compare(true, false)", +1, cmpResult);
    	
    	cmpResult = BBTrue1.compareTo(BBTrue1);
    	errorCount += Checkers.checker("BBTrue1.compareTo(BBTrue1)", 0, cmpResult);
    	cmpResult = BBTrue1.compareTo(BBFalse1);
    	errorCount += Checkers.checker("BBTrue1.compareTo(BBFalse1)", +1, cmpResult);
    	cmpResult = BBFalse1.compareTo(BBTrue1);
    	errorCount += Checkers.checker("BBFalse1.compareTo(BBTrue1)", -1, cmpResult);

    	errorCount += Checkers.checker("BBFalse1.equals(BBFalse2)?", true, BBFalse1.equals(BBFalse2));
    	errorCount += Checkers.checker("BBFalse1.equals(BBTrue1)?", false, BBFalse1.equals(BBTrue1));

    	errorCount += Checkers.checker("Boolean.logicalAnd(bbfalse, bbfalse)?", false, Boolean.logicalAnd(bbfalse, bbfalse));
    	errorCount += Checkers.checker("Boolean.logicalAnd(bbtrue, bbfalse)?", false, Boolean.logicalAnd(bbtrue, bbfalse));
    	errorCount += Checkers.checker("Boolean.logicalAnd(bbfalse, bbtrue)?", false, Boolean.logicalAnd(bbfalse, bbtrue));
    	errorCount += Checkers.checker("Boolean.logicalAnd(bbtrue, bbtrue)?", true, Boolean.logicalAnd(bbtrue, bbtrue));
    	
    	errorCount += Checkers.checker("Boolean.logicalOr(bbfalse, bbfalse)?", false, Boolean.logicalOr(bbfalse, bbfalse));
    	errorCount += Checkers.checker("Boolean.logicalOr(bbtrue, bbfalse)?", true, Boolean.logicalOr(bbtrue, bbfalse));
    	errorCount += Checkers.checker("Boolean.logicalOr(bbfalse, bbtrue)?", true, Boolean.logicalOr(bbfalse, bbtrue));
    	errorCount += Checkers.checker("Boolean.logicalOr(bbtrue, bbtrue)?", true, Boolean.logicalOr(bbtrue, bbtrue));
    	
    	errorCount += Checkers.checker("Boolean.logicalXor(bbfalse, bbfalse)?", false, Boolean.logicalXor(bbfalse, bbfalse));
    	errorCount += Checkers.checker("Boolean.logicalXor(bbtrue, bbfalse)?", true, Boolean.logicalXor(bbtrue, bbfalse));
    	errorCount += Checkers.checker("Boolean.logicalXor(bbfalse, bbtrue)?", true, Boolean.logicalXor(bbfalse, bbtrue));
    	errorCount += Checkers.checker("Boolean.logicalXor(bbtrue, bbtrue)?", false, Boolean.logicalXor(bbtrue, bbtrue));

        errorCount += Checkers.checker("BBFalse1.toString()?", "false", BBFalse1.toString());
        errorCount += Checkers.checker("BBTrue1.toString()?", "true", BBTrue1.toString());
        errorCount += Checkers.checker("Boolean.toString(bbfalse)?", "false", Boolean.toString(bbfalse));
        errorCount += Checkers.checker("Boolean.toString(bbtrue)?", "true", Boolean.toString(bbtrue));
        
        Checkers.theEnd(errorCount);

    }

}
