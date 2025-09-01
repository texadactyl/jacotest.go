// hacked from https://github.com/mikepound/enigma

import java.util.Arrays;
import java.util.Random;

import enigma.analysis.EnigmaAnalysis;
import enigma.analysis.EnigmaKey;
import enigma.analysis.ScoredEnigmaKey;
import enigma.analysis.fitness.*;
import enigma.hardware.Enigma;

public class main {

    // Compare 2 char arrays.
    public static boolean arraysEqual(char[] A, char[] B) {
        if (A.length != B.length)
            return false;
        for (int ix = 0; ix < A.length; ix++) {
            if (A[ix] != B[ix])
                return false;
        }
        return true;
    }

	private static void assertArrayEquals(char [] ca1, char [] ca2) {
		int len1 = ca1.length;
		int len2 = ca2.length;
		if (len1 != len2) {
			System.out.printf("*** ERROR, unequal char array lengths. len(ca1)=%d, len(ca2)=%d.\n", len1, len2);
			System.out.println(ca1.toString());
			System.out.println(ca2.toString());
			throw new AssertionError("array lengths");
		}
		if (! arraysEqual(ca1, ca2)) {
			System.out.println("*** ERROR, unequal char array values.");
			System.out.println(ca1.toString());
			System.out.println(ca2.toString());
			throw new AssertionError("array contents");
		}
	}

    private static void fitnessTests() {

		System.out.println("Fitness tests .....");
        FitnessFunction ioc = new IoCFitness();
        FitnessFunction bigrams = new BigramFitness();
        FitnessFunction quadgrams = new QuadramFitness();

        final long startTime = System.currentTimeMillis();

        // For those interested, these were the original settings
        // II V III / 7 4 19 / 12 2 20 / AF TV KO BL RW
        char[] ciphertext = "OZLUDYAKMGMXVFVARPMJIKVWPMBVWMOIDHYPLAYUWGBZFAFAFUQFZQISLEZMYPVBRDDLAGIHIFUJDFADORQOOMIZPYXDCBPWDSSNUSYZTJEWZPWFBWBMIEQXRFASZLOPPZRJKJSPPSTXKPUWYSKNMZZLHJDXJMMMDFODIHUBVCXMNICNYQBNQODFQLOGPZYXRJMTLMRKQAUQJPADHDZPFIKTQBFXAYMVSZPKXIQLOQCVRPKOBZSXIUBAAJBRSNAFDMLLBVSYXISFXQZKQJRIQHOSHVYJXIFUZRMXWJVWHCCYHCXYGRKMKBPWRDBXXRGABQBZRJDVHFPJZUSEBHWAEOGEUQFZEEBDCWNDHIAQDMHKPRVYHQGRDYQIOEOLUBGBSNXWPZCHLDZQBWBEWOCQDBAFGUVHNGCIKXEIZGIZHPJFCTMNNNAUXEVWTWACHOLOLSLTMDRZJZEVKKSSGUUTHVXXODSKTFGRUEIIXVWQYUIPIDBFPGLBYXZTCOQBCAHJYNSGDYLREYBRAKXGKQKWJEKWGAPTHGOMXJDSQKYHMFGOLXBSKVLGNZOAXGVTGXUIVFTGKPJU".toCharArray();

        // Begin by finding the best combination of rotors and start positions (returns top n)
        ScoredEnigmaKey[] rotorConfigurations = EnigmaAnalysis.findRotorConfiguration(ciphertext,
                EnigmaAnalysis.AvailableRotors.FIVE,
                "",
                10,
                ioc);

        System.out.println("\nTop 10 rotor configurations:");
        for (ScoredEnigmaKey key : rotorConfigurations) {
            System.out.println(String.format("Key %s %s %s / %d %d %d / %f",
                    key.rotors[0], key.rotors[1], key.rotors[2],
                    key.indicators[0], key.indicators[1], key.indicators[2],
                    key.getScore()));
        }
        System.out.println(String.format("\nCurrent decryption: %s\n",
                new String(new Enigma(rotorConfigurations[0]).encrypt(ciphertext))));

        // Next find the best ring settings for the best configuration (index 0)
        ScoredEnigmaKey rotorAndRingConfiguration = EnigmaAnalysis.findRingSettings(rotorConfigurations[0], ciphertext, bigrams);

        System.out.println(String.format("\nBest ring settings: %d %d %d",
                rotorAndRingConfiguration.rings[0], rotorAndRingConfiguration.rings[1], rotorAndRingConfiguration.rings[2]));
        System.out.println(String.format("Current decryption: %s\n",
                new String(new Enigma(rotorAndRingConfiguration).encrypt(ciphertext))));

        // Finally, perform hill climbing to find plugs one at a time
        ScoredEnigmaKey optimalKeyWithPlugs = EnigmaAnalysis.findPlugs(rotorAndRingConfiguration, 5, ciphertext, quadgrams);
        System.out.println(String.format("\nBest plugboard: %s", optimalKeyWithPlugs.plugboard));
        System.out.println(String.format("Final decryption: %s\n",
                new String(new Enigma(optimalKeyWithPlugs).encrypt(ciphertext))));

        final long endTime = System.currentTimeMillis();

        System.out.printf("Total execution time: %f seconds\n", (double) (endTime - startTime) / 1000.0);
    }

    private static void encryptTest() {
    
        // Basic settings
    	System.out.println("\nEncryption testing with basic settings .....");
        Enigma e = new Enigma(new String[] {"I", "II", "III"}, "B", new int[] {0,0,0}, new int[] {0,0,0}, "");
        String input = "ABCDEFGHIJKLMNOPQRSTUVWXYZAAAAAAAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String output = "BJELRQZVJWARXSNBXORSTNCFMEYHCXTGYJFLINHNXSHIUNTHEORXOPLOVFEKAGADSPNPCMHRVZCYECDAZIHVYGPITMSRZKGGHLSRBLHL";
        char[] ciphertext = e.encrypt(input.toCharArray());
        assertArrayEquals(output.toCharArray(), ciphertext);

        // Varied rotors
    	System.out.println("Encryption testing with varied rotors .....");
        e = new Enigma(new String[] {"VII", "V", "IV"}, "B", new int[] {10,5,12}, new int[] {1,2,3}, "");
        ciphertext = e.encrypt(input.toCharArray());
        output = "FOTYBPKLBZQSGZBOPUFYPFUSETWKNQQHVNHLKJZZZKHUBEJLGVUNIOYSDTEZJQHHAOYYZSENTGXNJCHEDFHQUCGCGJBURNSEDZSEPLQP";
        assertArrayEquals(output.toCharArray(), ciphertext);

        // Long input
    	System.out.println("Encryption testing with long input .....");
        e = new Enigma(new String[] {"III", "VI", "VIII"}, "B", new int[] {3,5,9}, new int[] {11,13,19}, "");
        char[] longInput = new char[500];
        for (int i = 0; i < 500; i++) longInput[i] = 'A';
        ciphertext = e.encrypt(longInput);
        output = "YJKJMFQKPCUOCKTEZQVXYZJWJFROVJMWJVXRCQYFCUVBRELVHRWGPYGCHVLBVJEVTTYVMWKJFOZHLJEXYXRDBEVEHVXKQSBPYZN" +
                "IQDCBGTDDWZQWLHIBQNTYPIEBMNINNGMUPPGLSZCBRJULOLNJSOEDLOBXXGEVTKCOTTLDZPHBUFKLWSFSRKOMXKZELBDJNRUDUCO" +
                "TNCGLIKVKMHHCYDEKFNOECFBWRIEFQQUFXKKGNTSTVHVITVHDFKIJIHOGMDSQUFMZCGGFZMJUKGDNDSNSJKWKENIRQKSUUHJYMIG" +
                "WWNMIESFRCVIBFSOUCLBYEEHMESHSGFDESQZJLTORNFBIFUWIFJTOPVMFQCFCFPYZOJFQRFQZTTTOECTDOOYTGVKEWPSZGHCTQRP" +
                "GZQOVTTOIEGGHEFDOVSUQLLGNOOWGLCLOWSISUGSVIHWCMSIUUSBWQIGWEWRKQFQQRZHMQJNKQTJFDIJYHDFCWTHXUOOCVRCVYOHL";
        assertArrayEquals(output.toCharArray(), ciphertext);
        
    }

    private static void decryptTest() {
    
    	System.out.println("\nDecryption testing .....");
        Random rand = new Random();
        String[] allRotors = new String[] {"I", "II", "III", "IV", "V", "VI", "VII", "VIII"};

        char[] input = new char[1000];
        for (int i = 0; i < 1000; i++) {
            input[i] = (char)(rand.nextInt(26) + 65);
        }

        for (int ix = 1; ix < 11; ix++) {
            // Random initialisation
            String[] rotors = new String[] { allRotors[rand.nextInt(8)],
                    allRotors[rand.nextInt(8)],
                    allRotors[rand.nextInt(8)]};

            int[] startingPositions = new int[] {rand.nextInt(26),rand.nextInt(26),rand.nextInt(26)};
            int[] ringSettings = new int[] {rand.nextInt(26), rand.nextInt(26), rand.nextInt(26)};

            // Machine 1 - Encryption
            System.out.printf("Decryption testing - encrypt with machine 1 .....\n", ix);
            Enigma e = new Enigma(rotors, "B", startingPositions, ringSettings, "");
            char[] ciphertext = e.encrypt(input);

            // Machine 2 - Decryption
            System.out.printf("Decryption testing - decrypt with machine 2 .....\n", ix);
            Enigma e2 = new Enigma(rotors, "B", startingPositions, ringSettings, "");
            char[] plaintext = e2.encrypt(ciphertext);

            assertArrayEquals(input, plaintext);
        }

    }

    private static void plugboardTest() {
    
        // Simple test - 4 plugs
    	System.out.println("Plugboard test with 4 plugs .....");
        Enigma e = new Enigma(new String[] {"I", "II", "III"}, "B", new int[] {0,0,0}, new int[] {0,0,0}, "AC FG JY LW");
        char[] input = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA".toCharArray();
        char[] output = e.encrypt(input);
        char[] expectedOutput = "QREBNMCYZELKQOJCGJVIVGLYEMUPCURPVPUMDIWXPPWROOQEGI".toCharArray();
        assertArrayEquals(expectedOutput, output);

        // 6 plugs
    	System.out.println("Plugboard test with 6 plugs .....");
        e = new Enigma(new String[] {"IV", "VI", "III"}, "B", new int[] {0,10,6}, new int[] {0,0,0}, "BM DH RS KN GZ FQ");
        input = "WRBHFRROSFHBCHVBENQFAGNYCGCRSTQYAJNROJAKVKXAHGUZHZVKWUTDGMBMSCYQSKABUGRVMIUOWAPKCMHYCRTSDEYTNJLVWNQY".toCharArray();
        expectedOutput = "FYTIDQIBHDONUPAUVPNKILDHDJGCWFVMJUFNJSFYZTSPITBURMCJEEAMZAZIJMZAVFCTYTKYORHYDDSXHBLQWPJBMSSWIPSWLENZ".toCharArray();
        output = e.encrypt(input);
        assertArrayEquals(expectedOutput, output);

        // 10 plugs
     	System.out.println("Plugboard test with 10 plugs .....");
        e = new Enigma(new String[] {"I", "II", "III"}, "B", new int[] {0,1,20}, new int[] {5,5,4}, "AG HR YT KI FL WE NM SD OP QJ");
        input = "RNXYAZUYTFNQFMBOLNYNYBUYPMWJUQSBYRHPOIRKQSIKBKEKEAJUNNVGUQDODVFQZHASHMQIHSQXICTSJNAUVZYIHVBBARPJADRH".toCharArray();
        expectedOutput = "CFBJTPYXROYGGVTGBUTEBURBXNUZGGRALBNXIQHVBFWPLZQSCEZWTAWCKKPRSWOGNYXLCOTQAWDRRKBCADTKZGPWSTNYIJGLVIUQ".toCharArray();
        output = e.encrypt(input);
        assertArrayEquals(expectedOutput, output);
        
    }
    
    public static void main(String[] args) {
    
    	System.out.println("Simulation of the Enigma Engine circa WWII.");
    	fitnessTests();
    	encryptTest();
    	decryptTest();
    	plugboardTest();
    	
    	Checkers.theEnd(0);
	}
	
}
