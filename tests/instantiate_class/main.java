public class main {
    
    public static void main(String args[]) {
        System.out.println("Library lib will be instantiated .....");
        Library lib = new Library();
        System.out.println("Library lib was instantiated");
        
        double d = -1.0;
        double absd = lib.absValue(d);
        System.out.print("Absolute value of -1.0: ");
        System.out.println(absd);
    }

}
