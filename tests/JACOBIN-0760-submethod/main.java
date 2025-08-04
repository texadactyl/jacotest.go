
public class main {

    public static void main(String[] args) {
        MotherClass mc = new MotherClass();
        mc.init();
        if (mc.obj.dipsyDoo("ABC"))
            System.out.println("Success!");
        else {
            System.out.println("*** Failed");
            System.exit(1);
        }
    }    

}
