// Second ZipInputStream exercise: covers more of the ZipInputStream / ZipEntry API surface.
// Assumes class Checkers is in the CLASSPATH and input.zip is in the current directory.
//
// No string concatenation (+) and no lambdas, so no INVOKEDYNAMIC is generated:
// labels are built with String.format() and callbacks are anonymous inner classes.
//
// Checkers API (argument order is label, expected, observed):
//     int  Checkers.checker(String label, <type> expected, <type> observed)  // 0 = OK, 1 = error
//     void Checkers.theEnd(int errorCount)

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class main {

    final static String IN_ZIP_FILE = "input.zip";

    static String[][] zipTable = { // file name, uncompressed size, uncompressed CRC
            {"0.main.log",        "1161",   "1216827134"},
            {"1.biostar.log",     "2257",   "1550240362"},
            {"1.pizerow.log",     "9502",   "3844065707"},
            {"1.rpi4.log",        "2139",   "2050768053"},
            {"2.pizerow.log",     "1744",   "1380747039"},
            {"2.rpi2.log",        "9598",   "3929933469"},
            {"2.zotac.log",       "17364",  "3963511932"},
            {"3.rpi2.log",        "1778",   "277903632"},
            {"3.rpi3.log",        "4492",   "1929418946"},
            {"3.rpi4.log",        "175008", "1706934587"},
            {"4.rpi3.log",        "20006",  "2491861642"},
            {"4.rpi4.log",        "1961",   "1267000413"},
            {"4.zotac.log",       "10885",  "1887076250"},
            {"5.zotac.log",       "1831",   "1064895305"},
            {"remcmd.cfg",        "192",    "2638084272"},
            {"remdown.cfg",       "393",    "921907977"},
            {"remping.cfg",       "189",    "4082986205"},
            {"remupd.cfg",        "325",    "2386648222"},
            {"remx_cfg.py",       "3541",   "1411510599"},
            {"remx_main.py",      "4089",   "4086809879"},
            {"remx_utilities.py", "6019",   "2295793381"}
    }; // 21 rows

    static int errorCount = 0;

    interface Thrower {
        void run() throws Exception;
    }

    static String lbl(String name, String what) {
        return String.format("%s %s", name, what);
    }

    // Verify that the action throws exactly the expected exception class.
    static void expect(String label, Class<?> expected, Thrower action) {
        String observed = "no exception";
        try {
            action.run();
        } catch (Throwable t) {
            observed = t.getClass().getName();
        }
        errorCount += Checkers.checker(label, expected.getName(), observed);
    }

    static int findRow(String name) {
        for (int ii = 0; ii < zipTable.length; ++ii) {
            if (name.equals(zipTable[ii][0])) return ii;
        }
        return -1;
    }

    // Pass 1: every read API, entry metadata, EOF semantics, charset constructor.
    static void pass1() throws IOException {
        System.out.println("--- Pass 1: read APIs and entry metadata");
        boolean[] seen = new boolean[zipTable.length];
        int fileCount = 0;

        try {
        	FileInputStream fs = new FileInputStream(IN_ZIP_FILE);
            //ZipInputStream zs = new ZipInputStream(fs, StandardCharsets.UTF_8);
            ZipInputStream zs = new ZipInputStream(fs);

            errorCount += Checkers.checker("markSupported", false, zs.markSupported());

            ZipEntry ze;
            while ((ze = zs.getNextEntry()) != null) {
                if (ze.isDirectory()) {
                    System.out.printf("Directory: %s\n", ze.getName());
                    continue;
                }
                String name = Paths.get(ze.getName()).getFileName().toString();
                int row = findRow(name);
                if (row < 0) {
                    System.out.printf("*** ERROR :: name %s not found\n", name);
                    ++errorCount;
                    continue;
                }
                seen[row] = true;
                long expSize = Long.parseLong(zipTable[row][1]);
                long expCrc = Long.parseLong(zipTable[row][2]);

                // ---- ZipEntry metadata (no data read yet)
                int method = ze.getMethod();
                errorCount += Checkers.checker(lbl(name, "method valid"), true, method == ZipEntry.STORED || method == ZipEntry.DEFLATED);
                errorCount += Checkers.checker(lbl(name, "toString"), ze.getName(), ze.toString());
                errorCount += Checkers.checker(lbl(name, "not directory"), false, ze.isDirectory());
                errorCount += Checkers.checker(lbl(name, "size (header)"), expSize, ze.getSize());
                errorCount += Checkers.checker(lbl(name, "crc (header)"), expCrc, ze.getCrc());
                errorCount += Checkers.checker(lbl(name, "available before read"), 1, zs.available());
                errorCount += Checkers.checker(lbl(name, "read(buf,0,0)"), 0, zs.read(new byte[1], 0, 0));

                // ---- Rotate through the read APIs
                int mode = fileCount % 6;
                byte[] data = null;
                long skipped = 0;
                switch (mode) {
                    case 0: { // read() one byte at a time
                        ByteArrayOutputStream bo = new ByteArrayOutputStream();
                        int b;
                        while ((b = zs.read()) != -1) bo.write(b);
                        data = bo.toByteArray();
                        break;
                    }
                    case 1: { // read(byte[], off, len) with a non-zero offset
                        final int OFF = 7, LEN = 4096;
                        byte[] buf = new byte[OFF + LEN];
                        ByteArrayOutputStream bo = new ByteArrayOutputStream();
                        int n;
                        while ((n = zs.read(buf, OFF, LEN)) != -1) {
                            errorCount += Checkers.checker(lbl(name, "read(buf,off,len) n > 0"), true, n > 0);
                            bo.write(buf, OFF, n);
                        }
                        data = bo.toByteArray();
                        break;
                    }
                    case 2: // readAllBytes()
                        data = zs.readAllBytes();
                        break;
                    case 3: // readNBytes(int) asking for more than is present
                        data = zs.readNBytes((int) expSize + 10);
                        break;
                    case 4: { // transferTo(OutputStream)
                        ByteArrayOutputStream bo = new ByteArrayOutputStream();
                        long moved = zs.transferTo(bo);
                        errorCount += Checkers.checker(lbl(name, "transferTo count"), expSize, moved);
                        data = bo.toByteArray();
                        break;
                    }
                    case 5: // skip(long) then readAllBytes()
                        skipped = zs.skip(expSize / 2);
                        errorCount += Checkers.checker(lbl(name, "skip count"), expSize / 2, skipped);
                        data = zs.readAllBytes();
                        break;
                }

                long total = skipped + data.length;
                errorCount += Checkers.checker(String.format("%s bytes read (mode %d)", name, mode), expSize, total);
                if (mode != 5) {
                    CRC32 crc = new CRC32();
                    crc.update(data, 0, data.length);
                    errorCount += Checkers.checker(String.format("%s crc computed (mode %d)", name, mode), expCrc, crc.getValue());
                }

                // ---- EOF semantics for the current entry
                errorCount += Checkers.checker(lbl(name, "read() at EOF"), -1, zs.read());
                errorCount += Checkers.checker(lbl(name, "read(buf) at EOF"), -1, zs.read(new byte[16], 0, 16));
                errorCount += Checkers.checker(lbl(name, "available at EOF"), 0, zs.available());
                errorCount += Checkers.checker(lbl(name, "skip at EOF"), 0, zs.skip(10));

                // ---- Entry metadata after the data has been consumed
                errorCount += Checkers.checker(lbl(name, "size (after)"), expSize, ze.getSize());
                errorCount += Checkers.checker(lbl(name, "crc (after)"), expCrc, ze.getCrc());
                errorCount += Checkers.checker(lbl(name, "compressed size known"), true, ze.getCompressedSize() >= 0);
                if (method == ZipEntry.STORED) {
                    errorCount += Checkers.checker(lbl(name, "stored: csize == size"), expSize, ze.getCompressedSize());
                }

                // Explicitly close every other entry; the rest are closed by getNextEntry().
                if (fileCount % 2 == 0) {
                    zs.closeEntry();
                    errorCount += Checkers.checker(lbl(name, "read() after closeEntry"), -1, zs.read());
                }
                ++fileCount;
            }

            errorCount += Checkers.checker("file count", zipTable.length, fileCount);
            for (int ii = 0; ii < seen.length; ++ii) {
                errorCount += Checkers.checker(lbl(zipTable[ii][0], "seen"), true, seen[ii]);
            }
            errorCount += Checkers.checker("getNextEntry after end", true, zs.getNextEntry() == null);
        } catch(Exception ex) {
        	String errMsg = String.format("*** ERROR, %s", ex.getMessage());
        	throw new AssertionError(errMsg);
        }
    }

    // Pass 2: closeEntry() before the data is consumed, exceptions, mark/reset, closed-stream behaviour.
    static void pass2() throws IOException {
        System.out.println("--- Pass 2: partial reads, exceptions, close()");

        // Partial read then closeEntry: enumeration must still find every entry.
        try (FileInputStream fs = new FileInputStream(IN_ZIP_FILE);
             ZipInputStream zs = new ZipInputStream(fs)) {
            int count = 0;
            ZipEntry ze;
            while ((ze = zs.getNextEntry()) != null) {
                if (ze.isDirectory()) continue;
                int first = zs.read();
                errorCount += Checkers.checker(lbl(ze.getName(), "first byte readable"), true, first >= 0);
                zs.closeEntry();
                errorCount += Checkers.checker(lbl(ze.getName(), "read() after early closeEntry"), -1, zs.read());
                ++count;
            }
            errorCount += Checkers.checker("entries via early closeEntry", zipTable.length, count);
        }

        // Exceptions on a stream positioned on the first entry.
        FileInputStream fs = new FileInputStream(IN_ZIP_FILE);
        final ZipInputStream zs = new ZipInputStream(fs);
        final byte[] buf = new byte[8];
        errorCount += Checkers.checker("first entry present", true, zs.getNextEntry() != null);

        expect("read(null,0,1)", NullPointerException.class, new Thrower() {
            public void run() throws Exception { zs.read(null, 0, 1); }
        });
        expect("read(buf,-1,1)", IndexOutOfBoundsException.class, new Thrower() {
            public void run() throws Exception { zs.read(buf, -1, 1); }
        });
        expect("read(buf,0,-1)", IndexOutOfBoundsException.class, new Thrower() {
            public void run() throws Exception { zs.read(buf, 0, -1); }
        });
        expect("read(buf,5,5)", IndexOutOfBoundsException.class, new Thrower() {
            public void run() throws Exception { zs.read(buf, 5, 5); }
        });
        expect("skip(-1)", IllegalArgumentException.class, new Thrower() {
            public void run() throws Exception { zs.skip(-1); }
        });
        expect("reset()", IOException.class, new Thrower() {
            public void run() throws Exception { zs.reset(); }
        });
        zs.mark(100); // documented no-op; must not throw

        // close(), then everything that needs an open stream must throw IOException.
        zs.close();
        zs.close(); // second close is harmless
        expect("getNextEntry after close", IOException.class, new Thrower() {
            public void run() throws Exception { zs.getNextEntry(); }
        });
        expect("read() after close", IOException.class, new Thrower() {
            public void run() throws Exception { zs.read(); }
        });
        expect("read(buf,0,8) after close", IOException.class, new Thrower() {
            public void run() throws Exception { zs.read(buf, 0, 8); }
        });
        expect("available() after close", IOException.class, new Thrower() {
            public void run() throws Exception { zs.available(); }
        });
        expect("closeEntry() after close", IOException.class, new Thrower() {
            public void run() throws Exception { zs.closeEntry(); }
        });
        expect("skip(1) after close", IOException.class, new Thrower() {
            public void run() throws Exception { zs.skip(1); }
        });
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Zip input file exercise #2");
        try {
            pass1();
            pass2();
        } catch (IOException ie) {
            ie.printStackTrace();
            ++errorCount;
        }
        Checkers.theEnd(errorCount);
    }
}
