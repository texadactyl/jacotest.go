public class main extends SuperClass {
    public main() {
        super(); // This implicitly uses invokespecial to call the SuperClass constructor
    }

    // An instance method that explicitly calls the superclass's native method
    public void callSuperNativeMethod() {
        super.nativeMethod(); // This uses invokevirtual, as it's the correct way to call superclass methods.
    }

    public static void main(String[] args) {
        new main().callSuperNativeMethod();
    }
}

class SuperClass {
    // Declare a native method
    public native void nativeMethod();

    //static {
    //    System.loadLibrary("NativeLib");
    //}
}

