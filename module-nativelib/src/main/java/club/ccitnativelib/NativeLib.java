package club.ccitnativelib;

public class NativeLib {

    // Used to load the 'ccitnativelib' library on application startup.
    static {
        System.loadLibrary("ccitnativelib");
    }

    /**
     * A native method that is implemented by the 'ccitnativelib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}