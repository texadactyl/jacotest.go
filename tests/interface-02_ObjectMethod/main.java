public class main {
    public static void main(String[] args) {
        I02 x = new C02();
        // Call toString(), which is a java.lang.Object method, via interface ref
        System.out.println(x.toString());
    }
}
