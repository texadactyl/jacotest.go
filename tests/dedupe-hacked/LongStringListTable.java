import java.util.HashMap;

/**
 * Data structure that has key of long integers and values consisting
 * of arrays of Strings.
 *
 * Fully array-based; avoids Set, Collection, keySet(), values(), entrySet().
 *
 * @author alb
 * @recoded by chatGPT.
 * @hacked-by R J Elkins
 */
public class LongStringListTable {

    private static final boolean debugging = true;

    /** Main data structure: key → array of filenames */
    private final HashMap<Long, String[]> table;

    /** Array of keys to enable iteration without using Set/Collection */
    private long[] keys;

    /** Number of keys currently stored */
    private int keyCount;
    
    /**
     * Constructs a new table with default initial capacity.
     */
    public LongStringListTable() {
        this(16); // default initial capacity
    }

    /**
     * Constructs a new table with a specified initial capacity.
     */
    public LongStringListTable(int initialCapacity) {
        table = new HashMap<>(initialCapacity);
        keys = new long[initialCapacity];
        keyCount = 0;
    }

    /**
     * Inserts a new filename associated with the numeric key.
     * If the key exists, appends the filename to the array.
     *
     * @param filename the filename to add
     * @param numeric  the numeric key
     */
    public void insertEntry(String filename, Long numeric) {
        String[] current = table.get(numeric);

        if (current == null) {
            // New key
            String[] newEntry = new String[1];
            newEntry[0] = filename;
            table.put(numeric, newEntry);

            // Add key to keys array, resizing if necessary
            if (keyCount >= keys.length) {
                long[] newKeys = new long[keys.length * 2];
                if (debugging) System.out.printf("DEBUG BEGIN insertEntry current == null and keyCount >= keys.length: arraycopy keyCount=%d, keys.length=%d\n", keyCount, keys.length);
                System.arraycopy(keys, 0, newKeys, 0, keys.length);
                if (debugging) System.out.printf("DEBUG END   insertEntry current == null and keyCount >= keys.length: arraycopy keyCount=%d, keys.length=%d\n", keyCount, keys.length);
                keys = newKeys;
            } else {
                if (debugging) System.out.printf("DEBUG insertEntry (NO arraycopy CALL) current == null and keyCount < keys.length: keyCount=%d, keys.length=%d\n", keyCount, keys.length);
            }
            keys[keyCount++] = numeric;
        } else {
            // Append to existing array
            String[] newEntry = new String[current.length + 1];
            if (debugging) System.out.printf("DEBUG BEGIN insertEntry current != null: arraycopy keyCount=%d, keys.length=%d\n", keyCount, keys.length);
            System.arraycopy(current, 0, newEntry, 0, current.length);
            if (debugging) System.out.printf("DEBUG END   insertEntry current != null: arraycopy keyCount=%d, keys.length=%d\n", keyCount, keys.length);
            newEntry[current.length] = filename;
            table.put(numeric, newEntry);
        }
    }

    /**
     * Returns all keys in the table as a long array.
     */
    public long[] getKeyArray() {
        long[] result = new long[keyCount];
        if (debugging) System.out.println("DEBUG BEGIN getKeyArray: arraycopy");
        System.arraycopy(keys, 0, result, 0, keyCount);
        if (debugging) System.out.println("DEBUG END   getKeyArray: arraycopy");
        return result;
    }

    /**
     * Returns all values (arrays of filenames) in the table.
     * The order corresponds to getKeyArray().
     */
    public String[][] getValuesArray() {
        String[][] values = new String[keyCount][];
        for (int i = 0; i < keyCount; i++) {
            values[i] = table.get(keys[i]);
        }
        return values;
    }

    /**
     * Returns the array of filenames for the given key,
     * or null if the key does not exist.
     */
    public String[] getEntry(long key) {
        return table.get(key);
    }

    /**
     * Returns the number of keys stored in the table.
     */
    public int size() {
        return keyCount;
    }
}

