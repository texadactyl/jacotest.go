public class main {
    public static void main(String[] args) {
    
        final long t1 = System.currentTimeMillis();
        var rounds = 10_000_000;

        double sum = 0.0;
        double flip = -1.0;
        for (long i = 1; i <= rounds; i++) {
            flip *= -1.0;
            sum += flip / (2 * i - 1);
        }

        final long t2 = System.currentTimeMillis();
        System.out.printf("Rounds: %d\n", rounds);
        double elapsedSeconds = (double)(t2 - t1) / 1000.0;
        System.out.printf("Elapsed time (seconds): %f.3\n", elapsedSeconds);
        System.out.println(sum * 4.0);
    }
}
