class Vehicle { ///////////////////////////  Superclass, Superclass

    private String category = "Automobile";

    Vehicle() {
        System.out.println("Vehicle constructor: I am constructed!");
    }

    public void honk() {
        System.out.println("Vehicle honk: Toot!");
    }

    public void ringer() {
        System.out.println("Vehicle ringer: Ding-dong!");
    }

    public String getCategory() {
        return category;
    }

}

class Car extends Vehicle { //////////////////////////////  Superclass, Subclass

    protected String brand = "Ford";

    Car() {
    	super();
        System.out.println("Car constructor: That was the Vehicle constructor!");
        System.out.print("Car constructor: I am a type of ");
        System.out.println(getCategory());
        System.out.print("Car constructor: My brand is ");
        System.out.println(brand);
        System.out.println("Car constructor: End");
    }

    public void honk() {
        System.out.println("Car honk: Toot!");
        System.out.println("Car honk: Here comes a super.honk followed by a super.ringer .....");
    	super.honk();
    	super.ringer();
        System.out.println("Car honk: End");
    }

    public String getBrand() {
        return brand;
    }

}

public class Model extends Car { ////////// Subclass

    private String modelName = "Mustang";

    Model() {
        super();
        System.out.println("Model constructor: That was the Car constructor!");
        System.out.print("Model constructor: I am a type of ");
        System.out.println(getCategory());
        System.out.print("Model constructor: My brand is ");
        System.out.println(getBrand());
        System.out.print("Model constructor: My model is ");
        System.out.println(modelName);
        System.out.println("Model constructor: End");
    }

    public String getModelName() {
        return modelName;
    }

    public void honk() {
        System.out.println("Model honk: Toot, toot!");
        System.out.println("Model honk: Here comes super.honk .....");
        super.honk();
        System.out.println("Model honk: Going to ring the bell 2 times .....");
        ringer();
        super.ringer();
        System.out.print("Model honk: The super.brand is ");
        System.out.println(super.brand);
        System.out.println("Model honk: End");
    }

}

