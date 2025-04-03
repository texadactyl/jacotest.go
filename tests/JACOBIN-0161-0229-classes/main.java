public class main {

    public static void printer(String label, String value) {
        System.out.print(label);
        System.out.print(" : ");
        System.out.println(value);
    }

    public static int checkNumEqual(String text, long xx, long yy) {
        if (xx == yy) return 0;
        System.out.print("*** ERROR, checkNumEqual: compare failed");
        System.out.print(text);
        System.out.print(" !! ");
        System.out.print(xx);
        System.out.print(" ");
        System.out.print("!=");
        System.out.print(" ");
        System.out.println(yy);
        return 1;
    }

    public static int checkNumUnequal(String text, long xx, long yy) {
        if (xx != yy) return 0;
        System.out.print("*** ERROR, checkNumUnequal: equal but should not be !! ");
        System.out.print(text);
        System.out.print(" !! ");
        System.out.print(xx);
        System.out.print(" ");
        System.out.print("!=");
        System.out.print(" ");
        System.out.println(yy);
        return 1;
    }

    public static int checkStrEqual(String text, String xx, String yy) {
        if (xx.equals(yy)) return 0;
        System.out.print("*** ERROR, checkStrEqual: compare failed !! ");
        System.out.print(text);
        System.out.print(" !! ");
        System.out.print(xx);
        System.out.print(" ");
        System.out.print("!=");
        System.out.print(" ");
        System.out.println(yy);
        return 1;
    }

    public static int checkStrUnequal(String text, String xx, String yy) {
        if (!xx.equals(yy)) return 0;
        System.out.print("*** ERROR, checkStrUnequal: equal but should not be !! ");
        System.out.print(text);
        System.out.print(" !! ");
        System.out.print(xx);
        System.out.print(" ");
        System.out.print("!=");
        System.out.print(" ");
        System.out.println(yy);
        return 1;
    }

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

        errorCount += checkStrEqual("insider.teething() == done", insider.teething(), "done");
        errorCount += checkNumEqual("insider.with_teeth == 42", insider.with_teeth, 42);

        System.out.println("Outsider class will now be instantiated .....");
        Outsider outsider = new Outsider();
        System.out.println("Outsider class was instantiated");

        String gimme_in = insider.gimmeString();
        printer("gimme_in", gimme_in);
        String gimme_out = outsider.gimmeString();
        printer("gimme_out", gimme_out);

        insider.iota += 1;
        errorCount += checkNumUnequal("insider.iota != outsider.iota", insider.iota, outsider.iota);
        outsider.iota += 1;
        errorCount += checkNumEqual("insider.iota == outsider.iota", insider.iota, outsider.iota);

        errorCount += checkNumUnequal("insider.lucretia != outsider.lucretia", insider.lucretia, outsider.lucretia);
        outsider.lucretia += 1;
        errorCount += checkNumEqual("insider.lucretia == outsider.lucretia", insider.lucretia, outsider.lucretia);

        errorCount += checkNumUnequal("insider.is_this_a_7() != outsider.is_this_a_7()", insider.is_this_a_7(), outsider.is_this_a_7());
        errorCount += checkNumEqual("insider.is_this_a_7() - 1 != outsider.is_this_a_7()", insider.is_this_a_7() - 1, outsider.is_this_a_7());

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

        errorCount += checkStrUnequal("red != rainbow", "red", "rainbow");
        errorCount += checkStrUnequal("red != orange", "red", "orange");
        errorCount += checkStrUnequal("loud != soft", "loud", "soft");
        errorCount += checkNumEqual("myRed.getNumber() == 42", myRed.getNumber(), 42);

        Pig myPig = new Pig();  // Create a Pig object implemented from Animal
        errorCount += checkStrEqual("myPig.getSound() == oink", myPig.getSound(), "oink");
        errorCount += checkStrEqual("myPig.getColor() == pink", myPig.getColor(), "pink");
 
         assert (errorCount == 0);
         System.out.println("Success!");
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


