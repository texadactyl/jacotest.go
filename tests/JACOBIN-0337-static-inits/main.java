// hacked from https://blogs.oracle.com/javamagazine/post/java-instance-initializer-block

class main {

    public static void main(String[] args) {

        InstanceInitializersII ii = new InstanceInitializersII();
        System.out.print("main j=100: ");
        System.out.println(ii.j);
        assert ii.j == 100;
        System.out.print("main k=50: ");
        System.out.println(ii.k);
        assert ii.k == 50;

        NonStaticForwardReferences objRef = new NonStaticForwardReferences();
        System.out.print("objRef nsf1=50: ");
        System.out.println(objRef.nsf1);
        assert objRef.nsf1 == 50;
        System.out.print("objRef nsf2=30: ");
        System.out.println(objRef.nsf2);
        assert objRef.nsf2 == 30;
        
        new AnonymousClassMaker().createAnonymous().print(); 
    }
}

class InstanceInitializersII {

    { //Instance initializer with forward references. (1)
        i = j = 10;                                 // (2) Permitted.
        int result = this.i * this.j;               // (3) i is 10, j is 10.

        System.out.print("II i=10: ");
        System.out.println(this.i);                 // (4) 10
        System.out.print("II j=10: ");
        System.out.println(this.j);                 // (5) 10
        System.out.print("II k=50: ");
        System.out.println(this.k);                 // (6) 50
    }

    // Instance field declarations.
    int i;             // (7) Field declaration without initializer expression
    int j = 100;       // (8) Field declaration with initializer expression.
    final int k = 50;  // (9) Final instance field with constant expression.
}

class NonStaticForwardReferences {

    {                                    // (1) Instance initializer block.
        nsf1 = 10;                        // (2) OK. Assignment to nsf1 allowed.
        nsf1 = sf1;                       // (3) OK. Static field access in nonstatic context.
        // int a = 2 * nsf1;              // (4) Not OK. Read operation before declaration.
        var b = nsf1 = 20;                // (5) OK. Assignment to nsf1 allowed.
        int c = this.nsf1;                // (6) OK. Not accessed by simple name.
    }

    int nsf1 = nsf2 = 30;                // (7) Nonstatic field. Assignment to nsf2 allowed.
    int nsf2;                            // (8) Nonstatic field.
    static int sf1 = 5;                  // (9) Static field.

    {                                    // (10) Instance initializer block.
        int d = 2 * nsf1;                 // (11) OK. Read operation after declaration.
        var e = nsf1 = 50;                // (12) OK. Assignment to nsf1 allowed.
    }

}

class Base { 
   	protected int a;
   	protected int b;
	void print() { 
        String msg = String.format("class Base a: : %d", a);
        System.out.println(msg); 
		assert a == 5;
	} 
} 

class AnonymousClassMaker { 
   Base createAnonymous() { 
      return new Base() {     // (1) Anonymous class
         {                    // (2) Instance initializer 
            a = 5; b = 10; 
         } 

         @Override
         void print() { 
            super.print(); 
            String msg = String.format("AnonymousClassMaker createAnonymous b: %d", b);
            System.out.println(msg); 
            assert b == 10;
         } 
      }; // end anonymous class 
   } 
}

