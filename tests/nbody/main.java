/*

Model the orbits of the Jovian planets, using the same simple symplectic-integrator.
Symplectic-integrator URL: https://janus.astro.umd.edu/HNBody/

The Computer Language Benchmarks Game
https://salsa.debian.org/benchmarksgame-team/benchmarksgame/

contributed by Mark C. Lewis
modifications by Chad Whipkey, Stefan Feldbinder, Tagir Valeev, and Han Kai
   
MAKE:
javac -d . -cp . nbody.java

COMMAND LINE:
java  -cp . nbody 50000000

STDOUT:
-0.169075164
-0.169059907

*/

public final class main {

    static final int ADVANCES = 50000; 

	public static void printBracketedObject(String label, Object value) {
	    System.out.print(label);
	    System.out.print(" = {");
	    System.out.print(value);
	    System.out.println("}");
	}

    public static void main(String[] args) {
      long t1, t2;
      double elapsedSeconds;
      int n = ADVANCES;

      String msg = "Model the orbits of the Jovian planets, using the Symplectic integration Package";
      System.out.println(msg);
      msg = "URL: https://janus.astro.umd.edu/HNBody/";
      System.out.println(msg);
        
      NBodySystem bodies = new NBodySystem();
      printBracketedObject("Initial energy", bodies.energy());
      t1 = System.currentTimeMillis();
      for (int i = 0; i < n; ++i)
         bodies.advance(0.01);
      t2 = System.currentTimeMillis();
      elapsedSeconds = (double)(t2 - t1) / 1000.0;
      printBracketedObject("Final energy", bodies.energy());
      printBracketedObject("Elapsed time (seconds)", elapsedSeconds);
      
      Checkers.theEnd(0);
	}

}

