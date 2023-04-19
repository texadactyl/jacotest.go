public class Library {

    public Library() {
        System.out.println("Instantiated the Library class");
    }
    
    public static double absValue(double argument) {
        if (argument < 0.0) return -argument;
        else return argument;
    }
}

