import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Exercises java.util.zip.ZipOutputStream.
 * Runs under HotSpot and Jacobin (Java 21, Windows and POSIX).
 * ASCII only, no string concatenation (no invokedynamic), no Locale.
 */
public class main {

    private static int errorCount = 0;
    private static final int TEST_COUNT = 17;

    private static final String ZE = "java.util.zip.ZipException";

    // ---------------------------------------------------------------- helpers

    private static String expect(String name, String content) {
        return String.format("%s=%s;", name, content);
    }

    private static byte[] bytes(String text) {
        return text.getBytes();
    }

    private static String repeated(String unit, int count) {
        StringBuilder sb = new StringBuilder();
        for (int ix = 0; ix < count; ix++) {
            sb.append(unit);
        }
        return sb.toString();
    }

    private static String readAll(ZipInputStream zis) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[512];
        int count;
        while ((count = zis.read(buf, 0, buf.length)) > 0) {
            bos.write(buf, 0, count);
        }
        return new String(bos.toByteArray());
    }

    /** Reads a whole archive with ZipInputStream: "name=content;name=content;" */
    private static String dump(byte[] zip) throws IOException {
        StringBuilder sb = new StringBuilder();
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zip));
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            sb.append(entry.getName());
            sb.append('=');
            sb.append(readAll(zis));
            sb.append(';');
        }
        zis.close();
        return sb.toString();
    }

    private static long crcOf(byte[] data) {
        CRC32 crc = new CRC32();
        crc.update(data, 0, data.length);
        return crc.getValue();
    }

    private static ZipEntry storedEntry(String name, byte[] data) {
        ZipEntry entry = new ZipEntry(name);
        entry.setMethod(ZipEntry.STORED);
        entry.setSize(data.length);
        entry.setCompressedSize(data.length);
        entry.setCrc(crcOf(data));
        return entry;
    }

    /** ZipOutputStream subclass so that the protected deflate() can be called. */
    private static class Probe extends ZipOutputStream {
        Probe(OutputStream out) {
            super(out);
        }

        void runDeflate() throws IOException {
            deflate();
        }
    }

    // ---------------------------------------------------------------- individual tests

    /** Constants, toString, constructor null check. */
    private static void test01() throws Exception {
        errorCount += Checkers.checker("t01 STORED", 0, ZipEntry.STORED);
        errorCount += Checkers.checker("t01 DEFLATED", 8, ZipOutputStream.DEFLATED);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(bos);
        String text = zos.toString();
        System.out.println(text);

        errorCount += Checkers.checker("t01 toString prefix", "true",
                String.valueOf(text.startsWith("java/util/zip/ZipOutputStream@")));
        errorCount += Checkers.checker("t01 toString stable", text, zos.toString());
        zos.close();

        String caught = "none";
        try {
            new ZipOutputStream(null);
        } catch (Throwable thr) {
            caught = thr.getClass().getName();
        }
        errorCount += Checkers.checker("t01 null stream", "java.lang.NullPointerException", caught);
    }

    /** One DEFLATED entry, round trip through ZipInputStream. */
    private static void test02() throws Exception {
        String payload = repeated("ABCDEFGHIJ", 200);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(bos);
        ZipEntry entry = new ZipEntry("deflated.txt");
        zos.putNextEntry(entry);
        zos.write(bytes(payload), 0, payload.length());
        zos.closeEntry();
        zos.close();

        errorCount += Checkers.checker("t02 round trip", expect("deflated.txt", payload), dump(bos.toByteArray()));
        errorCount += Checkers.checker("t02 entry size", payload.length(), entry.getSize());
        errorCount += Checkers.checker("t02 entry crc", crcOf(bytes(payload)), entry.getCrc());
        errorCount += Checkers.checker("t02 compressed smaller", "true",
                String.valueOf(entry.getCompressedSize() > 0 && entry.getCompressedSize() < payload.length()));
        errorCount += Checkers.checker("t02 method", ZipEntry.DEFLATED, entry.getMethod());
    }

    /** One STORED entry. */
    private static void test03() throws Exception {
        byte[] data = bytes("stored payload");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(bos);
        zos.putNextEntry(storedEntry("stored.txt", data));
        zos.write(data, 0, data.length);
        zos.closeEntry();
        zos.close();
        errorCount += Checkers.checker("t03 stored round trip", "stored.txt=stored payload;", dump(bos.toByteArray()));
    }

    /** Multiple entries, mixed methods, directory entry. */
    private static void test04() throws Exception {
        byte[] stored = bytes("second");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(bos);

        zos.putNextEntry(new ZipEntry("one.txt"));
        zos.write(bytes("first"), 0, 5);
        zos.closeEntry();

        zos.putNextEntry(new ZipEntry("dir/"));
        zos.closeEntry();

        zos.putNextEntry(storedEntry("dir/two.txt", stored));
        zos.write(stored, 0, stored.length);
        zos.closeEntry();

        zos.putNextEntry(new ZipEntry("three.txt"));
        zos.write(bytes("third"), 0, 5);
        zos.close();   // close() must close the open entry and finish

        errorCount += Checkers.checker("t04 multiple", "one.txt=first;dir/=;dir/two.txt=second;three.txt=third;",
                dump(bos.toByteArray()));
    }

    /** write(int), write(byte[]), write(byte[],int,int) inside one entry. */
    private static void test05() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(bos);
        zos.putNextEntry(new ZipEntry("w.txt"));
        zos.write(65);                           // write(int): 'A'
        zos.write(bytes("BCD"));                 // write(byte[])
        zos.write(bytes("xxEFxx"), 2, 2);        // write(byte[],int,int): "EF"
        zos.write(0x147);                        // write(int) keeps low 8 bits: 'G'
        zos.closeEntry();
        zos.close();
        errorCount += Checkers.checker("t05 writes", "w.txt=ABCDEFG;", dump(bos.toByteArray()));
    }

    /** flush() in the middle of an entry. */
    private static void test06() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(bos);
        zos.putNextEntry(new ZipEntry("f.txt"));
        zos.write(bytes("before-"), 0, 7);
        zos.flush();
        zos.write(bytes("after"), 0, 5);
        zos.flush();
        zos.closeEntry();
        zos.flush();
        zos.close();
        errorCount += Checkers.checker("t06 flush", "f.txt=before-after;", dump(bos.toByteArray()));
    }

    /** Protected deflate() invoked through a subclass. */
    private static void test07() throws Exception {
        String payload = repeated("0123456789", 100);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Probe zos = new Probe(bos);
        zos.putNextEntry(new ZipEntry("d.txt"));
        zos.write(bytes(payload), 0, payload.length());
        zos.runDeflate();
        zos.runDeflate();
        zos.closeEntry();
        zos.close();
        errorCount += Checkers.checker("t07 deflate", expect("d.txt", payload), dump(bos.toByteArray()));
    }

    /** setComment: the comment is the tail of the archive. */
    private static void test08() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(bos);
        zos.setComment("hello zip");
        zos.putNextEntry(new ZipEntry("c.txt"));
        zos.write(bytes("x"), 0, 1);
        zos.closeEntry();
        zos.close();
        byte[] zip = bos.toByteArray();
        String tail = new String(zip, zip.length - 9, 9);
        errorCount += Checkers.checker("t08 comment tail", "hello zip", tail);
        errorCount += Checkers.checker("t08 comment entry", "c.txt=x;", dump(zip));

        String caught = "none";
        try {
            new ZipOutputStream(new ByteArrayOutputStream()).setComment(repeated("c", 65536));
        } catch (Throwable thr) {
            caught = thr.getClass().getName();
        }
        errorCount += Checkers.checker("t08 comment too long", "java.lang.IllegalArgumentException", caught);
    }

    /** setLevel and setMethod, including invalid values. */
    private static void test09() throws Exception {
        String payload = repeated("level-test ", 300);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(bos);
        zos.setLevel(Deflater.BEST_COMPRESSION);
        zos.putNextEntry(new ZipEntry("best.txt"));
        zos.write(bytes(payload), 0, payload.length());
        zos.closeEntry();
        zos.setLevel(Deflater.NO_COMPRESSION);
        zos.putNextEntry(new ZipEntry("none.txt"));
        zos.write(bytes(payload), 0, payload.length());
        zos.closeEntry();
        zos.setMethod(ZipOutputStream.STORED);
        byte[] data = bytes("stored by default method");
        ZipEntry stored = new ZipEntry("def.txt");   // method left at default (-1)
        stored.setSize(data.length);
        stored.setCompressedSize(data.length);
        stored.setCrc(crcOf(data));        zos.putNextEntry(stored);
        zos.write(data, 0, data.length);
        zos.closeEntry();
        zos.close();
        errorCount += Checkers.checker("t09 levels", expect("best.txt", payload).concat(expect("none.txt", payload))
                .concat(expect("def.txt", "stored by default method")), dump(bos.toByteArray()));

        String caughtLevel = "none";
        try {
            new ZipOutputStream(new ByteArrayOutputStream()).setLevel(99);
        } catch (Throwable thr) {
            caughtLevel = thr.getClass().getName();
        }
        errorCount += Checkers.checker("t09 invalid level", "java.lang.IllegalArgumentException", caughtLevel);

        String caughtMethod = "none";
        try {
            new ZipOutputStream(new ByteArrayOutputStream()).setMethod(5);
        } catch (Throwable thr) {
            caughtMethod = thr.getClass().getName();
        }
        errorCount += Checkers.checker("t09 invalid method", "java.lang.IllegalArgumentException", caughtMethod);
    }

    /** Duplicate entry and write with no current entry. */
    private static void test10() throws Exception {
        ZipOutputStream zos = new ZipOutputStream(new ByteArrayOutputStream());
        zos.putNextEntry(new ZipEntry("dup.txt"));
        zos.closeEntry();
        String caught = "none";
        String message = "none";
        try {
            zos.putNextEntry(new ZipEntry("dup.txt"));
        } catch (Throwable thr) {
            caught = thr.getClass().getName();
            message = thr.getMessage();
        }
        errorCount += Checkers.checker("t10 duplicate class", ZE, caught);
        errorCount += Checkers.containedIn("t10 duplicate message", "duplicate entry: dup.txt", message);

        // closeEntry with no open entry is a no-op
        zos.closeEntry();

        caught = "none";
        message = "none";
        try {
            zos.write(bytes("orphan"), 0, 6);
        } catch (Throwable thr) {
            caught = thr.getClass().getName();
            message = thr.getMessage();
        }
        errorCount += Checkers.checker("t10 no entry class", ZE, caught);
        errorCount += Checkers.containedIn("t10 no entry message", "no current ZIP entry", message);
        zos.close();
    }

    /** Use after close. */
    private static void test11() throws Exception {
        ZipOutputStream zos = new ZipOutputStream(new ByteArrayOutputStream());
        zos.putNextEntry(new ZipEntry("a.txt"));
        zos.close();
        zos.close();   // second close is harmless

        String caught = "none";
        try {
            zos.putNextEntry(new ZipEntry("b.txt"));
        } catch (Throwable thr) {
            caught = thr.getClass().getName();
        }
        errorCount += Checkers.checker("t11 putNextEntry after close", "java.io.IOException", caught);

        caught = "none";
        try {
            zos.write(bytes("z"), 0, 1);
        } catch (Throwable thr) {
            caught = thr.getClass().getName();
        }
        errorCount += Checkers.checker("t11 write after close", "java.io.IOException", caught);
    }

    /** STORED entry validation: bad CRC, too many bytes, missing size. */
    private static void test12() throws Exception {
        byte[] data = bytes("stored check");

        ZipEntry badCrc = storedEntry("badcrc.txt", data);
        badCrc.setCrc(crcOf(data) ^ 1L);
        ZipOutputStream zos = new ZipOutputStream(new ByteArrayOutputStream());
        zos.putNextEntry(badCrc);
        zos.write(data, 0, data.length);
        String caught = "none";
        try {
            zos.closeEntry();
        } catch (Throwable thr) {
            caught = thr.getClass().getName();
        }
        errorCount += Checkers.checker("t12 bad crc", ZE, caught);

        zos = new ZipOutputStream(new ByteArrayOutputStream());
        zos.putNextEntry(storedEntry("toolong.txt", data));
        caught = "none";
        try {
            zos.write(bytes("stored check plus extra"), 0, 23);
        } catch (Throwable thr) {
            caught = thr.getClass().getName();
        }
        errorCount += Checkers.checker("t12 too many bytes", ZE, caught);

        zos = new ZipOutputStream(new ByteArrayOutputStream());
        ZipEntry noSize = new ZipEntry("nosize.txt");
        noSize.setMethod(ZipEntry.STORED);
        caught = "none";
        try {
            zos.putNextEntry(noSize);
        } catch (Throwable thr) {
            caught = thr.getClass().getName();
        }
        errorCount += Checkers.checker("t12 stored without size", ZE, caught);
    }

    /** Empty archive. */
    private static void test13() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(bos);
        zos.finish();
        zos.close();
        errorCount += Checkers.checker("t13 empty archive size", 22, bos.size());
        errorCount += Checkers.checker("t13 empty archive entries", "", dump(bos.toByteArray()));
    }

    /** finish() followed by close(); the underlying stream stays correct. */
    private static void test14() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(bos);
        zos.putNextEntry(new ZipEntry("fin.txt"));
        zos.write(bytes("finish"), 0, 6);
        zos.finish();
        int sizeAfterFinish = bos.size();
        zos.finish();   // second finish is a no-op
        zos.close();
        errorCount += Checkers.checker("t14 finish idempotent", sizeAfterFinish, bos.size());
        errorCount += Checkers.checker("t14 finish entry", "fin.txt=finish;", dump(bos.toByteArray()));
    }

    /** Larger entry written in odd-sized chunks. */
    private static void test15() throws Exception {
        String payload = repeated("The quick brown fox jumps over the lazy dog. ", 400);
        byte[] data = bytes(payload);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(bos);
        zos.putNextEntry(new ZipEntry("big.txt"));
        int offset = 0;
        int chunk = 7;
        while (offset < data.length) {
            int count = Math.min(chunk, data.length - offset);
            zos.write(data, offset, count);
            offset += count;
            chunk = chunk + 13;
        }
        zos.close();
        errorCount += Checkers.checker("t15 chunked", expect("big.txt", payload), dump(bos.toByteArray()));
    }

    /** Write to a real file and read it back. */
    private static void test16() throws Exception {
        File file = File.createTempFile("zos", ".zip");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ZipOutputStream zos = new ZipOutputStream(fos);
            zos.putNextEntry(new ZipEntry("file1.txt"));
            zos.write(bytes("in a file"), 0, 9);
            zos.closeEntry();
            zos.close();

            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[256];
            int count;
            while ((count = fis.read(buf, 0, buf.length)) > 0) {
                bos.write(buf, 0, count);
            }
            fis.close();
            errorCount += Checkers.checker("t16 file round trip", "file1.txt=in a file;", dump(bos.toByteArray()));
            errorCount += Checkers.checker("t16 file nonempty", "true", String.valueOf(file.length() > 0));
        } finally {
            file.delete();
        }
    }

    // ---------------------------------------------------------------- driver

    private static void runTest(int number) throws Exception {
        switch (number) {
            case 1: test01(); break;
            case 2: test02(); break;
            case 3: test03(); break;
            case 4: test04(); break;
            case 5: test05(); break;
            case 6: test06(); break;
            case 7: test07(); break;
            case 8: test08(); break;
            case 9: test09(); break;
            case 10: test10(); break;
            case 11: test11(); break;
            case 12: test12(); break;
            case 13: test13(); break;
            case 14: test14(); break;
            case 15: test15(); break;
            case 16: test16(); break;
            default: break;
        }
    }

    public static void main(String[] args) {
        for (int number = 1; number <= TEST_COUNT; number++) {
            try {
                runTest(number);
            } catch (Throwable thr) {
                errorCount++;
                System.out.println(String.format("test%02d threw %s: %s",
                        number, thr.getClass().getName(), thr.getMessage()));
            }
        }
        Checkers.theEnd(errorCount);
    }
}
