public class main {
    public static void main(String args[]) {
		System.out.println("Investigate the following:");
		System.out.println("Found class: java/security/SecureRandom, but it did not contain method: nextLong");
		int a = (int) (Math.random() * 256);
		System.out.println("Cannot reproduce!");
    }
}
