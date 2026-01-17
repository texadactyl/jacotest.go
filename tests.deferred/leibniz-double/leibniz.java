public class leibniz {
    public static void main(String[] args) {
        var rounds = 1_000_000_000;

        double sum = 0.0;
        double flip = -1.0;
        for (long i = 1; i <= rounds; i++) {
            flip *= -1.0;
            sum += flip / (2 * i - 1);
        }

        System.out.println(sum * 4.0);
    }
}
