class C07 implements I07A, I07B {
    // Must override to resolve the conflict between I07A.greet() and I07B.greet().
    @Override
    public String greet() {
        // We can explicitly choose either default using InterfaceName.super.method()
        String ret = String.format("%s | %s", I07A.super.greet(), I07B.super.greet());
        return ret;
    }
}
