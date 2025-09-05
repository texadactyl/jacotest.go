import java.io.FileInputStream;
import java.io.IOException;

public class main {

    private static final int RECORD_LENGTH = 80;
    private static final int BLOCK_LENGTH = 2880;

    public static void main(String[] args) {
        int errorCount = 0;
        String filename;
        if (args.length == 0) {
            filename = "./nasa.fits";
        } else {
            filename = args[0];
        }
        
        int hduNumber = 1;
        try {
            FileInputStream fis = new FileInputStream(filename);
            while (true) {
                System.out.printf("=== HDU %d ===\n", hduNumber);
                int dataSize = readHeader(fis, hduNumber);
                System.out.printf("=== dataSize %d ===\n", dataSize);
                if (dataSize < 0) {
                    System.out.println("No more HDUs");
                    break;
                }
                switch (hduNumber) {
                    case 1:
                        errorCount += Checkers.checker("HDU 1 size", 34560, dataSize);
                        break;
                    case 2:
                        errorCount += Checkers.checker("HDU 2 size", 2880, dataSize);
                        break;
                    default:
                        System.out.println(String.format("*** ERROR, there should not be an HDU %d", hduNumber));
                        errorCount++;
                }
                
                skipData(fis, dataSize);
                hduNumber++;
            }
        } catch (IOException ex) {
            System.err.printf("*** ERROR, IOException: %s\n", ex.getMessage());
            System.exit(1);
        }
        
        errorCount += Checkers.checker("final HDU number", 3, hduNumber);
        Checkers.theEnd(errorCount);
        
    }

    /**
     * Reads and prints one HDU header.
     * Returns data size in bytes, or -1 if no HDU.
     */
    private static int readHeader(FileInputStream in, int hduNumber) throws IOException {
        byte[] block = new byte[BLOCK_LENGTH];

        int bitpix = 0;
        int naxis = 0;
        int[] naxisn = null;

        boolean firstCardSeen = false;
        boolean sawBitpix = false;
        boolean sawNaxis = false;
        boolean sawEnd = false;

        while (true) {
            int n = in.readNBytes(block, 0, BLOCK_LENGTH);
            if (n < 1) {
                if (firstCardSeen) {
                    throw new IOException("Unexpected EOF inside HDU header");
                }
                return -1; // EOF, no more HDUs
            }
            if (n < BLOCK_LENGTH) {
                throw new IOException(String.format("Truncated FITS header block, read %d bytes, BLOCK_LENGTH=%d", n, BLOCK_LENGTH));
            }

            for (int i = 0; i < BLOCK_LENGTH; i += RECORD_LENGTH) {
                String card = new String(block, i, RECORD_LENGTH);
                String keyword = card.substring(0, 8).trim();

                if (!firstCardSeen) {
                    // Verify the first card of this HDU
                    if (hduNumber == 1 && !keyword.equals("SIMPLE")) {
                        throw new IOException("First HDU must start with SIMPLE, found: " + keyword);
                    }
                    if (hduNumber > 1 && !keyword.equals("XTENSION")) {
                        throw new IOException("Extension HDU must start with XTENSION, found: " + keyword);
                    }
                    firstCardSeen = true;
                }

                printCard(card);

                if (keyword.equals("BITPIX")) {
                    bitpix = Integer.parseInt(card.substring(10, 30).trim());
                    sawBitpix = true;
                } else if (keyword.equals("NAXIS")) {
                    naxis = Integer.parseInt(card.substring(10, 30).trim());
                    naxisn = new int[naxis];
                    sawNaxis = true;
                } else if (keyword.startsWith("NAXIS") && keyword.length() > 5 && Character.isDigit(keyword.charAt(5))) {
                    int axis = Integer.parseInt(keyword.substring(5));
                    if (axis >= 1 && axis <= naxis) {
                        naxisn[axis - 1] = Integer.parseInt(card.substring(10, 30).trim());
                    }
                } else if (keyword.equals("END")) {
                    sawEnd = true;
                    if (!sawBitpix) {
                        throw new IOException(String.format("Missing required BITPIX keyword in HDU %d", hduNumber));
                    }
                    if (!sawNaxis) {
                        throw new IOException(String.format("Missing required NAXIS keyword in HDU %d", hduNumber));
                    }
                    int dataSize = computeDataSize(bitpix, naxis, naxisn);
                    return dataSize;
                }
            }
        }
    }

    /** Prints one FITS header card neatly. */
    private static void printCard(String card) {
        String keyword = card.substring(0, 8).trim();
        String value = "";
        String comment = "";

        if (card.length() >= 10 && card.charAt(8) == '=') {
            int slash = card.indexOf('/', 10);
            if (slash > 0) {
                value = card.substring(10, slash).trim();
                comment = card.substring(slash + 1).trim();
            } else {
                value = card.substring(10).trim();
            }
        } else {
            comment = card.substring(8).trim();
        }

        System.out.printf("Keyword: %-8s Value: %-20s Comment: %s%n", keyword, value, comment);
    }

    /** Compute data size from header parameters, padded to next 2880 boundary. */
    private static int computeDataSize(int bitpix, int naxis, int[] naxisn) {
        if (naxis == 0 || naxisn == null) {
            return 0;
        }

        long elements = 1;
        for (int n : naxisn) {
            elements *= n;
        }
        long bytesPerElement = Math.abs(bitpix) / 8;
        long size = elements * bytesPerElement;

        long padded = ((size + BLOCK_LENGTH - 1) / BLOCK_LENGTH) * BLOCK_LENGTH;
        return (int) padded;
    }

    /** Skip the data unit of given size. */
    private static void skipData(FileInputStream in, int dataSize) throws IOException {
        long skipped = 0;
        while (skipped < dataSize) {
            long s = in.skip(dataSize - skipped);
            if (s <= 0) {
                throw new IOException("*** ERROR, Unable to skip data section");
            }
            skipped += s;
        }
    }
}

