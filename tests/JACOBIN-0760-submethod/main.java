
public class main {

    public static void main(String[] args) {
        MotherClass mc = new MotherClass();
        mc.init();
        if (mc.obj.dipsyDoo("ABC"))
            Checkers.theEnd(0);
        else {
            Checkers.theEnd(1);
        }
    }    

}
