public class main {
    public static void main(String[] args) {
        C08 c = new C08();
        System.out.println(c.greet("World"));

        // Static interface methods are not inherited by implementing classes; call via interface name
        System.out.println(I08.shout("hi there"));

        // The following would not compile (by design):
        // System.out.println(c.shout("hi"));
    }
}
