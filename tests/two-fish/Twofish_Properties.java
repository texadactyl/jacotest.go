// hacked from https://www.schneier.com/academic/twofish/download/
// $Id: $
//
// $Log: $
// Revision 1.0  1998/03/24  raif
// + start of history.
//
// $Endlog$
/*
 * Copyright (c) 1997, 1998 Systemics Ltd on behalf of
 * the Cryptix Development Team. All rights reserved.
 */
//package Twofish;

/**
 * This class acts as a central repository for an algorithm specific
 * properties. It reads an (algorithm).properties file containing algorithm-
 * specific properties. When using the AES-Kit, this (algorithm).properties
 * file is located in the (algorithm).jar file produced by the "jarit" batch/
 * script command.<p>
 *
 * <b>Copyright</b> &copy; 1997, 1998
 * <a href="http://www.systemics.com/">Systemics Ltd</a> on behalf of the
 * <a href="http://www.systemics.com/docs/cryptix/">Cryptix Development Team</a>.
 * <br>All rights reserved.<p>
 *
 * <b>$Revision: $</b>
 *
 * @author David Hopwood
 * @author Jill Baker
 * @author Raif S. Naffah
 */
public class Twofish_Properties // implicit no-argument constructor
{
// Constants and variables with relevant static code
//...........................................................................

    static final boolean GLOBAL_DEBUG = false;

    static final String ALGORITHM = "Twofish";
    static final double VERSION = 0.2;
    static final String FULL_NAME = ALGORITHM + " ver. " + VERSION;
    static final String NAME = "Twofish_Properties";

    /**
     * Default properties in case .properties file was not found.
     */
    private static final String[][] params = {
            {"Trace.Twofish_Algorithm", "true"},
            {"Debug.Level.*", "1"},
            {"Debug.Level.Twofish_Algorithm", "9"},
    };

    /**
     * Get the value of a property for this algorithm.
     */
    public static String getProperty(String key) {
        for (int ii = 0; ii < params.length; ii++) {
            if ( key.equals(params[ii][0]) )
                return params[ii][1];
        }
        return null;
    }

    /**
     * Get the value of a property for this algorithm, or return
     * <i>value</i> if the property does not exist.
     */
    public static String getProperty(String key, String value) {
        for (int ii = 0; ii < params.length; ii++) {
            if ( key.equals(params[ii][0]) )
                return params[ii][1];
        }
        String errValue = String.format("<i>%s</i>)", value);
        return errValue;
    }

    /**
     * List algorithm properties to standard output.
     */
    public static void list() {
        System.out.println("#");
        String arg = "# ----- Begin " + ALGORITHM + " properties -----";
        System.out.println(arg);
        System.out.println("#");
        String key, value;
        for (int ii = 0; ii < params.length; ii++) {
            key = params[ii][0];
            value = params[ii][1];
            System.out.println(key + " = " + value);
        }
        System.out.println("#");
        arg = "# ----- End " + ALGORITHM + " properties -----";
        System.out.println(arg);
    }

// Developer support: Tracing and debugging enquiry methods (package-private)
//...........................................................................

    /**
     * Return true if tracing is requested for a given class.<p>
     * <p>
     * User indicates this by setting the tracing <code>boolean</code>
     * property for <i>label</i> in the <code>(algorithm).properties</code>
     * file. The property's key is "<code>Trace.<i>label</i></code>".<p>
     *
     * @param label The name of a class.
     * @return True iff a boolean true value is set for a property with
     * the key <code>Trace.<i>label</i></code>.
     */
    static boolean isTraceable(String label) {
    	String arg = "Trace.".concat(label);
        String s = getProperty(arg);
        if (s == null)
            return false;
        //return new Boolean(s).booleanValue();
        return Boolean.getBoolean(s);
    }

    /**
     * Return the debug level for a given class.<p>
     * <p>
     * User indicates this by setting the numeric property with key
     * "<code>Debug.Level.<i>label</i></code>".<p>
     * <p>
     * If this property is not set, "<code>Debug.Level.*</code>" is looked up
     * next. If neither property is set, or if the first property found is
     * not a valid decimal integer, then this method returns 0.
     *
     * @param label The name of a class.
     * @return The required debugging level for the designated class.
     */
    static int getLevel(String label) {
    	String arg = "Debug.Level.".concat(label);
        String s = getProperty(arg);
        if (s == null) {
            s = getProperty("Debug.Level.*");
            if (s == null)
                return 0;
        }
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
