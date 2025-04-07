package enigma.analysis.fitness;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Stream;

public class BigramFitness extends FitnessFunction {
    private float[] bigrams;

    private static int biIndex(int a, int b) {
        return (a << 5) | b;
    }

    private static void floatArrayFill(float[] array, float value) {
        for (int ix = 0; ix < array.length; ix++)
            array[ix] = value;
    }

    public BigramFitness() {
        this.bigrams = new float[826];
        floatArrayFill(this.bigrams, (float)Math.log10(epsilon));
        try {
            FileInputStream fis = new FileInputStream("./data/bigrams");
            InputStreamReader rdr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(rdr);
            String strLine;
            String strArray[];
            String key;
            int index;
            for(int ix = 0; ix < this.bigrams.length; ix++) {
                strLine = br.readLine();
                if (strLine == null) break;
                strArray = strLine.split(",");
                key = strArray[0];
                index = biIndex(key.charAt(0) - 65, key.charAt(1) - 65);
                //System.out.printf("DEBUG [%d] code=%s, index=%d, value=%f\n", ix, strArray[0], index, Float.parseFloat(strArray[1]));
                this.bigrams[index] = Float.parseFloat(strArray[1]);
            }
        } catch (IOException e) {
            String errMsg = e.getMessage();
            System.out.printf("IOException, %s\n", errMsg);
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            String errMsg = e.getMessage();
            System.out.printf("Some other Exception, %s\n", errMsg);
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public float score(char[] text) {
        float fitness = 0;
        int current = 0;
        int next = text[0] - 65;
        for (int i = 1; i < text.length; i++) {
            current = next;
            next = text[i] - 65;
            fitness += this.bigrams[biIndex(current, next)];
        }
        return fitness;
    }
}
