class main {

    public static void main(String[] args) {
    
    	System.out.println("Try a switch structure with an integer argument");

        for (int ii = 0; ii < 3; ii++) {

            switch (ii) {
                case 0:
                    System.out.println("case 0");
                    break;
                case 1:
                    System.out.println("case 1");
                    break;
                case 2:
                    System.out.println("case 2");
                    break;
                default:
                	System.out.printf("default...why? ii=%d\n", ii);
                    throw new AssertionError("switch-default failure");
            }

        }

    }

}
  
