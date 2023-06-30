public class ProcCharacter {

    public static int procCharacter() {
        int errorCount = 0;
        Character AA = 'A';
        String ss;

        System.out.println("\n========== procCharacter begins");


        if (Character.isLetter(AA))
            System.out.println("Success: Character.isLetter(AA)");
        else {
            errorCount += 1;
            System.out.println("*** ERROR, Character.isLetter(AA) is true, observed false");
        }

        if (!Character.isDigit(AA))
            System.out.println("Success: not Character.isDigit(AA)");
        else {
            errorCount += 1;
            System.out.println("*** ERROR, Character.isDigit(AA) is false, observed true");
        }

        char aa = Character.toLowerCase(AA);
        if (aa == 'a')
            System.out.println("Success: Lower case of AA is 'a'");
        else {
            errorCount += 1;
            System.out.print("*** ERROR, Lower case of AA is 'a', observed: ");
            System.out.println(aa);
        }

        ss = Character.toString(AA);
        if (ss.equals("A"))
            System.out.println("Success: Character.String(AA) is \"a\"");
        else {
            errorCount += 1;
            System.out.print("*** ERROR, Character.String(AA) is \"a\", observed: ");
            System.out.println(ss);
        }

        System.out.println("========== procCharacter ends");

        // Return error count to caller
        return errorCount;
    }

}
