public class main {

    static class Insider extends Outsider {
        int with_teeth;

        public Insider() {
            this.with_teeth = 41;
            this.lucretia = 45;
        }

        public String teething() {
            this.with_teeth = 42;
            return "done";
        }

        public int is_this_a_7() {
            return 8;
        }

    }

    public static void main(String args[]) {
        String wstr;
        int errorCount = 0;

        System.out.println("Testing subclasses that are embedded in the main class, parallel to main, and resident in a separate file.");
        System.out.println("Testing polymorphism, abstract classes, abstract methods, and interfaces.");

        AbstractMultiMedia myMyRed = new MyRed();
        errorCount += Checkers.checker("myMyRed.getColor() == \"reddish\"", "reddish", myMyRed.getColor());

        System.out.println("\nInsider class will now be instantiated .....");
        Insider insider = new Insider();
        System.out.println("Insider class was instantiated");

        errorCount += Checkers.checker("insider.teething() == done", "done", insider.teething());
        errorCount += Checkers.checker("insider.with_teeth == 42", 42, insider.with_teeth);

        System.out.println("Outsider class will now be instantiated .....");
        Outsider outsider = new Outsider();
        System.out.println("Outsider class was instantiated");

        String gimme_in = insider.gimmeString();
        System.out.printf("gimme_in: %s\n", gimme_in);
        String gimme_out = outsider.gimmeString();
        System.out.printf("gimme_out: %s\n", gimme_out);

        insider.iota += 1;
        errorCount += Checkers.checker("outsider.iota != insider.iota", false, outsider.iota == insider.iota);
        outsider.iota += 1;
        errorCount += Checkers.checker("outsider.iota == insider.iota", outsider.iota, insider.iota);

        errorCount += Checkers.checker("outsider.lucretia != insider.lucretia", false, outsider.lucretia == insider.lucretia);
        outsider.lucretia += 1;
        errorCount += Checkers.checker("outsider.lucretia == insider.lucretia", outsider.lucretia, insider.lucretia);

        errorCount += Checkers.checker("outsider.is_this_a_7() != insider.is_this_a_7()", false, outsider.is_this_a_7() == insider.is_this_a_7());
        errorCount += Checkers.checker("outsider.is_this_a_7() == insider.is_this_a_7() - 1", outsider.is_this_a_7(), insider.is_this_a_7() - 1);

        AbstractMultiMedia myMyOrange = new MyOrange();
        errorCount += Checkers.checker("myMyRed.getColor() != orange", false, myMyRed.getColor().equals("orange"));
        errorCount += Checkers.checker("myMyRed.getSound() == \"loud\"", "loud", myMyRed.getSound());
        errorCount += Checkers.checker("myMyRed.getNumber() == 42", 42, myMyRed.getNumber());

        Pig myPig = new Pig();  // Create a Pig object implemented from Animal
        errorCount += Checkers.checker("myPig.getSound() == oink", "oink", myPig.getSound());
        errorCount += Checkers.checker("myPig.getColor() == pink", "pink", myPig.getColor());
 
        Checkers.theEnd(errorCount);
    }

}

abstract class AbstractMultiMedia {
    public String getColor() {
        return ("rainbow");
    }

    public abstract String getSound();

    public int getNumber() {
        return 42;
    }
}

class MyRed extends AbstractMultiMedia {
    public String getColor() {
        return ("reddish");
    }

    public String getSound() {
        return ("loud");
    }
}

class MyOrange extends AbstractMultiMedia {
    public String getColor() {
        return ("orange");
    }

    public String getSound() {
        return ("soft");
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


