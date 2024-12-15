public class main {

    public static void main(String[] args) {
    
        DES des = new DES();
        String[] mainArray = des.level1();
        for (int ix = 0; ix < 3; ix++) 
            System.out.printf("mainArray[%d]: %s\n", ix, mainArray[ix]);
        System.out.println("Here come the des.objectArray elements .....");
        for (int ix = 0; ix < 3; ix++) 
            System.out.printf("des.objectArray[%d]: %s\n", ix, des.objectArray[ix]);
    
    }
}

class DES {

    public String[] objectArray = new String[3];
    
    public String[] level1() {
        String[] level1Array = level2(level3());
        return level1Array;
    }
    
    private String[] level2(String[] arg) {
        return arg;
    }
    
    private String[] level3() {
        String[] level3Array = new String[3];
        level3Array[0] = "1";
        level3Array[1] = "2";
        level3Array[2] = "3";
        objectArray = level3Array;
        return level3Array;
    }

}
