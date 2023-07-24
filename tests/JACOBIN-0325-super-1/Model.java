class Vehicle { ///////////////////////////  Superclass

  protected String brand = "Ford";
  
  Vehicle() {
    System.out.println("Vehicle constructor: I am constructed!");
  }
  
  public void honk() {
    System.out.println("Vehicle honk: .........(drum roll) ............ Toot, toot, tooo-ooot!");
  }
  
  public String getBrand() {
  	return brand;
  }

}

public class Model extends Vehicle { ////////// Subclass

  private String modelName = "Mustang";
  
  Model() {
    super();
    System.out.println("Model constructor: And that was the Vehicle constructor!");
  }

  public String getModelName() {
  	return modelName;
  }

  public void honk() {
    System.out.println("Model honk: Toot, toot!");
    System.out.println("Model honk: Here comes super.honk .....");
    super.honk();
    System.out.print("Model honk: The super.brand is ");
    System.out.println(super.brand);
    System.out.println("Model honk: Ta ta!");
  }

}

