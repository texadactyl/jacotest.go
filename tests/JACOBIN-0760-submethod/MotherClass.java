public class MotherClass {

    MotherClass obj;
    boolean success;
    
    public void init() {
        obj = new Subbie();
        System.out.println("MotherClass init() ran.");
    }

    public boolean dipsyDoo(String arg) { 
        System.out.printf("*** ERROR, I should have been replaced by an extending dipsyDoo function, arg: %s !!!\n", arg);
        return false; 
    }
    
    public class Subbie extends MotherClass {
    
        public boolean dipsyDoo(String arg) { 
            System.out.printf("Subbie dipsyDoo, arg: %s\n", arg); 
            return true;
        }
        
    }

}

