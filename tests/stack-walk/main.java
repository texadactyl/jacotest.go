import java.lang.StackTraceElement;
import java.lang.Thread;

public class main {

    static int errorCount = 0;
    
    public static void main(String args[]) {
        System.out.println("Walk a stack trace at 3 levels");
        Thread th = Thread.currentThread();
        System.out.println("main (level1) .....");
        dispStack(th, "main");
        Level2.level2(th);       
        Checkers.theEnd(errorCount);
    }

    private class Level2 {
        private static void level2(Thread th) {
            System.out.println("\nlevel2 .....");
            dispStack(th, "Level2");
            Level3.level3(th);
        }
    }

    private class Level3 {

        private static void level3(Thread th) {
            System.out.println("\nlevel3 .....");
            dispStack(th, "Level3");
        }

    }
    
    private static void dispStack(Thread th, String level) {
    
        StackTraceElement steArray[] = th.getStackTrace();
        String className, fileName, methodName;
        int lineNumber;
        for (int ii = 0; ii < steArray.length; ii++) {
            String methName = steArray[ii].getMethodName();
            System.out.printf("[%d] loader: %s, class: %s, file[line]: %s[%d], meth: %s, module: %s\n",
                ii, steArray[ii].getClassLoaderName(), steArray[ii].getClassName(), steArray[ii].getFileName(), steArray[ii].getLineNumber(), 
                methName, steArray[ii].getModuleName() );
            switch (methName) {
                case "dispStack":
                    errorCount += Checkers.checker("dispStack line", 36, steArray[ii].getLineNumber());
                    break;
                case "level3":
                    errorCount += Checkers.checker("Level3 line", 29, steArray[ii].getLineNumber());
                    break;
            }
        }
        
    }

}
