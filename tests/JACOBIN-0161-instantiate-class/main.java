public class main {
    
    public static void main(String args[]) {
        String msg = "Testing one instantiation of a class residing in a separate source file";
        System.out.println(msg);
        
        System.out.println("Library lib will be instantiated .....");
        Library lib = new Library();
        System.out.println("Library lib was instantiated");
        
        double d = -1.0;
        double absd = Library.absValue(d);
        System.out.print("Absolute value of -1.0: ");
        System.out.println(absd);
    }

}
