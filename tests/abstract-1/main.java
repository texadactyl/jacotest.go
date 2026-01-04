public class main {

    public static void main(String args[]) {
        int errorCount = 0;

        Animal cat = new Cat();
        errorCount += Checkers.checker("cat.takeString -> \"cat\"", "cat", cat.takeString());
        errorCount += Checkers.checker("cat.takeNumber -> 1", 1, cat.takeNumber());

        Animal dog = new Dog();
        errorCount += Checkers.checker("dog.takeString -> \"dog\"", "dog", dog.takeString());
        errorCount += Checkers.checker("dog.takeNumber -> 2", 2, dog.takeNumber());
        
        errorCount += Checkers.checker("dog.beetlejuice -> \"beetlejuice\"", "beetlejuice", dog.beetlejuice());
 
        Checkers.theEnd(errorCount);
    }

}

abstract class Animal {

    // Abstract method - no implementation
    abstract String takeString();
    
    // Concrete method - has implementation
    int takeNumber() {
        return 42;
    }
    
    // Concrete method not replaced by one in the subclass
    String beetlejuice() {
        return "beetlejuice";
    }
    
}

class Cat extends Animal {

    String takeString() {
        return "cat";
    }

    int takeNumber() {
        return 1;
    }
}

class Dog extends Animal {

    String takeString() {
        return "dog";
    }

    @Override
    int takeNumber() {
        return 2;
    }
}

