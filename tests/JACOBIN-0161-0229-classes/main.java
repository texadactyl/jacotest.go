public class main {

    static class Insider extends Outsider {
        byte with_teeth;
        
        public Insider() {
            this.with_teeth = (byte) 41;
            this.lucretia = 45;
        }
        
        public String teething() {
            this.with_teeth = (byte) 42;
            return "done";
        }
        
        public int is_this_a_7() {
            return 8;
        }

    }
    
    public static void printer(String label, String value) {
        System.out.print(label);
        System.out.print(" : ");
        System.out.println(value);
    }
    
    public static void main(String args[]) {
        String wstr;
        
        System.out.println("Testing subclasses that are embedded in the main class, parallel to main, and resident in a separate file.");
        System.out.println("Testing polymorphism, abstract classes, abstract methods, and interfaces.");
        System.out.println("\nInsider class will be instantiated .....");
        Insider insider = new Insider();
        System.out.println("Insider class was instantiated");
        wstr = String.valueOf(insider.with_teeth);
        printer("insider.with_teeth before calling teething", wstr);
        assert insider.with_teeth == (byte) 41;
        wstr = insider.teething();
        assert wstr == "done";
        wstr = String.valueOf(insider.with_teeth);
        printer("insider.with_teeth after calling teething", wstr);
        assert insider.with_teeth == (byte) 42;
        
        System.out.println("Outsider class will be instantiated .....");
        Outsider outsider = new Outsider();
        System.out.println("Outsider class was instantiated");
        
        String gimme_in = insider.gimmeString();
        printer("gimme_in", gimme_in);
        String gimme_out = outsider.gimmeString();
        printer("gimme_out", gimme_out);
        assert gimme_in == gimme_out;
       
        insider.iota += 1;
        assert insider.iota != outsider.iota;

        outsider.iota += 1;
        assert insider.iota == outsider.iota;

        assert insider.lucretia != outsider.iota;
        insider.lucretia -= 1;
        assert insider.lucretia == outsider.iota;

        assert insider.is_this_a_7() != outsider.is_this_a_7();
        assert (insider.is_this_a_7() - 1) == outsider.is_this_a_7();
        
        MultiMedia myRed = new Red();
        MultiMedia myOrange = new Orange();
        
        String red = myRed.getColor();
        String orange = myOrange.getColor();
        
        String loud = myRed.getSound();
        String soft = myOrange.getSound();
        
        assert red != "rainbow";
        assert red != orange;
        assert loud != soft;
        assert myRed.getNumber() == 42;

        Pig myPig = new Pig();  // Create a Pig object implemented from Animal
        assert myPig.getSound() == "oink";
        assert myPig.getColor() == "pink";
    }
    
}

abstract class MultiMedia {
    public String getColor() {
        return("rainbow");
    }
    public abstract String getSound();
    public int getNumber() {
        return 42;
    }
}

class Red extends MultiMedia {
  public String getColor() {
    return("red");
  }
  public String getSound() {
    return("loud");
  }
}

class Orange extends MultiMedia {
  public String getColor() {
    return("orange");
  }
  public String getSound() {
    return("soft");
  }
}

// The animal interface
interface Animal {
  public String getSound();
  public String getColor();
}

// Pig implements the Animal interface
class Pig implements Animal {
  public String getSound() {
    // The body of animalSound() is provided here
    return "oink";
  }
  public String getColor() {
    // The body of sleep() is provided here
    return "pink";
  }
}


