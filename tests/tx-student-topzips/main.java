/*
    A Texas university asked me to produce this program in Python (included bar chart output).
    ChatGPT converted it to Java (alpha version!).
*/

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class main {

    // -----------------------------------------------------------------
    // Simple struct-like class for state/zipcode/count
    private static class Entry {
        String state;
        String zipcode;
        int count;

        Entry(String argState, String argZipcode) {
            this.state = argState;
            this.zipcode = argZipcode;
            this.count = 1;
        }

        // used for expected value comparison
        Entry(String argState, String argZipcode, int count) {
            this.state = argState;
            this.zipcode = argZipcode;
            this.count = count;
        }
    }

    // -----------------------------------------------------------------
    // Normalize zipcode to 5 digits or return null on error input.
    private static String normalizeZip(String input) {
        if (input == null) return null;
        StringBuilder digits = new StringBuilder();
        for (int ix = 0; ix < input.length(); ix++) {
            char ch = input.charAt(ix);
            if (ch >= '0' && ch <= '9') {
                digits.append(ch);
            }
        }
        if (digits.length() < 5) return null;
        return digits.substring(0, 5);
    }

    // -----------------------------------------------------------------
    // Find an existing entry or return -1 if not found
    private static int findEntry(Entry[] entries, int size, String state, String zipcode) {
        for (int ix = 0; ix < size; ix++) {
            Entry entry = entries[ix];
            if (entry.state.equals(state) && entry.zipcode.equals(zipcode)) {
                return ix;
            }
        }
        return -1;
    }

    // -----------------------------------------------------------------
    /* Sort entries descending by 
            1. count (descending) 
            2. state (ascending) 
            3. zipcode (ascending)
    */
    private static void bubbleSort(Entry[] entries, int size) {
        for (int ix = 0; ix < size - 1; ix++) {
            for (int jx = 0; jx < size - ix - 1; jx++) {
                Entry entryA = entries[jx];
                Entry entryB = entries[jx + 1];

                boolean swap = false;

                // Primary key: count descending
                if (entryA.count < entryB.count) {
                    swap = true;
                } else if (entryA.count == entryB.count) {
                    // Secondary key: state ascending
                    int stateCmp = entryA.state.compareTo(entryB.state);
                    if (stateCmp > 0) {
                        swap = true;
                    } else if (stateCmp == 0) {
                        // Tertiary key: zip ascending
                        if (entryA.zipcode.compareTo(entryB.zipcode) > 0) {
                            swap = true;
                        }
                    }
                }

                if (swap) {
                    Entry tmp = entries[jx];
                    entries[jx] = entries[jx + 1];
                    entries[jx + 1] = tmp;
                }
            }
        }
    }
    
    // -----------------------------------------------------------------
    public static void main(String[] args) {
        String filename = "input.csv";
        int maxBars = 30;
        int errorCount = 0;

        if (args.length > 0) {
            filename = args[0];
        }
        if (args.length > 1) {
            try {
                maxBars = Integer.parseInt(args[1]);
                if (maxBars <= 0) {
                    System.err.println("maxBars must be positive. Using default = 30");
                    maxBars = 30;
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid maxBars argument. Using default = 30");
            }
        }

        Entry[] entries = new Entry[10000]; // maximum unique state+zipcode combos
        int entryCount = 0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))) {
            String line;
            boolean headerSkipped = false;
            while ((line = br.readLine()) != null) {
                // Skip header line
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length < 2) continue;

                String stateRaw = parts[0].trim().toUpperCase();
                String zipRaw = parts[1].trim();
                String zip = normalizeZip(zipRaw);

                if (stateRaw.isEmpty() || zip == null) continue;

                int idx = findEntry(entries, entryCount, stateRaw, zip);
                if (idx >= 0) {
                    entries[idx].count++;
                } else {
                    if (entryCount >= entries.length) {
                        System.err.println("Too many unique state/zipcode combinations.");
                        break;
                    }
                    entries[entryCount++] = new Entry(stateRaw, zip);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        // Sort descending by count
        bubbleSort(entries, entryCount);

        // Print results
        System.out.printf("%-10s %-8s %s%n", "STATE", "ZIPCD", "COUNT");
        System.out.println("--------------------------------");
        int limit = Math.min(entryCount, maxBars);
        for (int ix = 0; ix < limit; ix++) {
            Entry entry = entries[ix];
            System.out.printf("%-10s %-8s %d%n", entry.state, entry.zipcode, entry.count);
        }

        System.out.println("--------------------------------");
        System.out.printf("Displayed top %d of %d total entries.\n", limit, entryCount);

        Entry[] expected = new Entry[] {
                new Entry("TX", "75206", 12),
                new Entry("TX", "75231", 6),
                new Entry("TX", "75248", 6),
                new Entry("TX", "75043", 5),
                new Entry("KS", "67037", 4),
                new Entry("TX", "75052", 4),
        };
        limit = Math.min(limit, expected.length);
        for (int ix = 0; ix < limit; ix++) {
            Entry entry = entries[ix];
            errorCount += Checkers.checker("State", expected[ix].state, entry.state);
            errorCount += Checkers.checker("Zipcode", expected[ix].zipcode, entry.zipcode);
            errorCount += Checkers.checker("Count", expected[ix].count, entry.count);
        }

        Checkers.theEnd(errorCount);
    }
}

