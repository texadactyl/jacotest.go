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

        MultiMedia myRed = new Red();
        MultiMedia myOrange = new Orange();

        String red = myRed.getColor();
        System.out.printf("red.getColor = %s\n", red);
        String orange = myOrange.getColor();
        System.out.printf("orange.getColor = %s\n", orange);

        String loud = myRed.getSound();
        System.out.printf("red.getSound = %s\n", loud);
        String soft = myOrange.getSound();
        System.out.printf("orange.getSound = %s\n", soft);

        errorCount += Checkers.checker("myRed.getColor() == \"red\"", "red", myRed.getColor());
        errorCount += Checkers.checker("myRed.getColor() != orange", false, myRed.getColor().equals("orange"));
        errorCount += Checkers.checker("myRed.getSound() == \"loud\"", "loud", myRed.getSound());
        errorCount += Checkers.checker("myRed.getNumber() == 42", 42, myRed.getNumber());

        Pig myPig = new Pig();  // Create a Pig object implemented from Animal
        errorCount += Checkers.checker("myPig.getSound() == oink", "oink", myPig.getSound());
        errorCount += Checkers.checker("myPig.getColor() == pink", "pink", myPig.getColor());
 
        Checkers.theEnd(errorCount);
    }

}

abstract class MultiMedia {
    public String getColor() {
        return ("rainbow");
    }

    public abstract String getSound();

    public int getNumber() {
        return 42;
    }
}

class Red extends MultiMedia {
    public String getColor() {
        return ("red");
    }

    public String getSound() {
        return ("loud");
    }
}

class Orange extends MultiMedia {
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


