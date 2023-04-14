public class main {

    public static void main(String[] args) throws Exception {
        int errorCount = 0;
        
        errorCount += ProcCharacter.procCharacter();
        errorCount += ProcDouble.procDouble();
        errorCount += ProcInteger.procInteger();
        errorCount += ProcString.procString();
        
        System.out.print("\nError count = ");
        System.out.println(errorCount);
        if (errorCount > 0) {
            System.out.println("Going to thrrow an Exception next .....");
            throw new Exception("Test case failure !!");
        }
    }
}
