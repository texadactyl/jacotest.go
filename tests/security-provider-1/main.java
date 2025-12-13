import java.util.*;

public class main {

    static int errorCount = 0;
    static String PROVIDER_NAME;
    static String jvmPgmName;
    

    public static void main(String args[]) {

        jvmPgmName = jj._getProgramName();
        if (jvmPgmName.equals("java")) 
            PROVIDER_NAME = java.security.Security.getProviders()[0].getName();
        else
            PROVIDER_NAME = "GoSecurityProvider";
        System.out.println("Java security provider #1 tests");
        System.out.printf("jvmPgmName=%s\n", jvmPgmName);
        
        TestProviderFields();
        TestSecurityGetProviders();

        Checkers.theEnd(errorCount);
    }

    // ------------------------ Tests ------------------------

    static private void TestProviderFields() {
        java.security.Provider p = java.security.Security.getProvider(PROVIDER_NAME);
        if (p == null) {
            System.out.println("*** ERROR, java.security.Security.getProvider() failed");
            ++errorCount;
            Checkers.theEnd(errorCount);
        }

        String pName = p.getName();
        System.out.printf("Provider Name: %s\n", pName);
        System.out.printf("Provider Info: %s\n", p.getInfo());

        errorCount += Checkers.checker("Provider name", PROVIDER_NAME, pName);
    }

    static private void TestSecurityGetProviders() {
        java.security.Provider[] providers = java.security.Security.getProviders();
        if (!jvmPgmName.equals("java")) 
            errorCount += Checkers.checker("providers list length", 1, providers.length);
        for (int ix = 0; ix < providers.length; ix++) 
            System.out.printf("provider [%d]: %s\n", ix, providers[ix].getName());
        errorCount += Checkers.checker("providers list[0] name", PROVIDER_NAME, providers[0].getName());
    }

}

// Class jj in case we are executed by the OpenJDK JVM.
class jj {

    public static String _getProgramName() {
        System.out.println("J-class function _getProgramName (not Jacobin)");
        return "java";
    }
   
}
