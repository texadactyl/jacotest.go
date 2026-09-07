public final class SyncPrintf {

    // no object instances
    private SyncPrintf() { }

    /**
     * Formats the given arguments per the format string and appends
     * the result to the supplied Appendable.
     */
    public static synchronized void syncPrintf(Appendable out, String format, Object... args) {
        String formatted = String.format(format, args);
        System.out.print(formatted);
    }

    /**
     * Convenience overload that writes to System.out.
     */
    public static synchronized void syncPrintf(String format, Object... args) {
        syncPrintf(System.out, format, args);
    }

    /**
     * Convenience overload that returns the formatted String directly.
     */
    public static synchronized String syncSprintf(String format, Object... args) {
        return String.format(format, args);
    }
}
