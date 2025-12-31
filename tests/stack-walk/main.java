import java.util.List;
import java.util.stream.Collectors;
import java.lang.Thread;
import java.lang.Exception;

public class main {

    static int errorCount = 0;

    public static void main(String args[]) throws Exception {
        System.out.println("main (level1) .....");
        level2(Thread.currentThread().getStackTrace()[1].getLineNumber());       
        Checkers.theEnd(errorCount);
    }

    private static void level2(int level1Line) throws Exception {
        System.out.println("level2 .....");
        Level3.level3(level1Line, Thread.currentThread().getStackTrace()[1].getLineNumber());
    }

    private class Level3 {

        private static void level3(int level1Line, int level2Line) throws Exception {

            String msg, className, methodName;
            int lineNumber;
            int level3Line;

            System.out.println("level3 .....");

            // *** The next two Java executable statements should not be edited.
            final List<StackWalker.StackFrame> stack = StackWalker.getInstance().walk(s -> s.collect(Collectors.toList()));
            level3Line = Thread.currentThread().getStackTrace()[1].getLineNumber() - 1;
            // *** The previous two Java executable statements should not be edited.

            System.out.printf("level3: level1Line: %d, level2Line: %d, level3Line: %d\n",
                    level1Line, level2Line, level3Line);
            System.out.println("level3: Stack:");

            // Walk the stack.
            int counter = 0;
            for (StackWalker.StackFrame sf : stack) {
            
                counter += 1;
                className = sf.getClassName();
                methodName = sf.getMethodName();
                lineNumber = sf.getLineNumber();
                
                switch(counter) {

                    case 1:
                        errorCount += Checkers.checker("case 1 class", className, "main$Level3");
                        errorCount += Checkers.checker("case 1 method", methodName, "level3");
                        errorCount += Checkers.checker("case 1 line", lineNumber, level3Line);
                        break;

                    case 2:
                        errorCount += Checkers.checker("case 2 class", className, "main");
                        errorCount += Checkers.checker("case 2 method", methodName, "level2");
                        errorCount += Checkers.checker("case 2 line", lineNumber, level2Line);
                        break;

                    case 3:
                        errorCount += Checkers.checker("case 3 class", className, "main");
                        errorCount += Checkers.checker("case 3 method", methodName, "main");
                        errorCount += Checkers.checker("case 3 line", lineNumber, level1Line);
                        break;

                    default:
                        Checkers.fail("stack size exceeds 3!!");

                } // switch

            } // for (StackWalker.StackFrame sf : stack)

        } // Level3.level3

    } // class Level3

}
