class main {

    public static void main(String[] args) {

        for (int ii = 0; ii < 3; ii++) {

            switch (ii) {
                case 0:
                    System.out.println("0");
                    break;
                case 1:
                    System.out.println("1");
                    break;
                case 2:
                    System.out.println("2");
                    break;
                default:
                    System.out.println("*** ERROR, switch-default failure");
                    System.exit(1);
            }

        }

    }

}
  
