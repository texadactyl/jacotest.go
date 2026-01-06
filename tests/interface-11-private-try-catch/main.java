public class main {
    public static void main(String[] args) {
        C08 c = new C08();
        System.out.println("ok C08 c = new C08();");
        String msg = c.greet("World");
        System.out.println(msg);

        // Static interface methods are not inherited by implementing classes; call via interface name
        System.out.println(I08.shout("Survival is success!"));
    }
}
