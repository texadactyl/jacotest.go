public class main {

	public static void main(String[] args) {

		System.out.println("=== Two levels of superclass not counting Object ===");
		System.out.println("main: Next, I will instantiate a Model.");
		Model myModel = new Model();

		System.out.println("main: Let's toot the horn.");
		myModel.honk();

		System.out.print("main: It's a ");
		System.out.print(myModel.getBrand());
		System.out.print(" ");
		System.out.print(myModel.getModelName());
		System.out.println("!");
		
		Checkers.theEnd(0);
		
	}

}
