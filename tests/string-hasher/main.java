public class main {

    static int errorCount = 0;

    private static void hasher(long arg, int expected) {
        String str = String.valueOf(arg);
        int observed = str.hashCode();
        errorCount += Checkers.checker(String.format("hasher(%d)", arg), expected, observed);
    }

    private static void hasher(String arg, int expected) {
        int observed = arg.hashCode();
        errorCount += Checkers.checker(String.format("hasher(%s)", arg), expected, observed);
    }

    public static void main(String[] args) {
        
        hasher(0, 48);
        hasher(1, 49);
        hasher(100, 48625);
        hasher(1000, 1507423);
        hasher(32767, 48643705);
        hasher(65535, 51501624);
        hasher(1000000 - 1, 1686256992);
        hasher(1000000000 - 1, 1344611257);
        hasher("93326215443944152681699238856266700490715968264381621468592963895217599993229915608941463976156518286253697920827223758251185210916864000000000000000000000000", -1686438134);
        hasher("1000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000007", 2050518118);

        hasher(-1, 1444);
        hasher(-100, 1389220);
        hasher(-1000, 43065868);
        hasher(-32767, 1336955500);
        hasher(-65535, 1339813419);
        hasher(-1000000 - 1, -1219900251);
        hasher(-1000000000 - 1, 1964899085);
        hasher("-93326215443944152681699238856266700490715968264381621468592963895217599993229915608941463976156518286253697920827223758251185210916864000000000000000000000000", -1702731145);
        hasher("-1000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000007", 557101587);
        
        hasher("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()_-+={[}]:;\"'<,>.?/|\\~`", 492534987);
                
        Checkers.theEnd(errorCount);
   }
}

