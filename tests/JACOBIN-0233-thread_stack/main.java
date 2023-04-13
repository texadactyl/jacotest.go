public class main {

    public static void main(String args[]) {
        StackTraceElement[] stktrace = Thread.currentThread().getStackTrace();
        String str = stktrace[1].getFileName() + ":" + String.valueOf(stktrace[1].getLineNumber());
        System.out.print("Here I am: ");
        System.out.println(str);
    }
    
}

